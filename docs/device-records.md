# Device-owned opaque records

`AppDatabase.deviceRecords` supplies a raw store for bounded inputs that must survive
before an account session is available. It does not grant account access or implement
notification policy. Callers encrypt private content before insertion, authenticate
it before use, keep identifying information out of keys, and own recovery and deletion.
The existing account-owned replica, commands and blobs keep their admission rules.

Each record has a namespace, opaque ID, fresh generation and encoded payload. Keys
are 1–128 ASCII letters, digits, periods, underscores or hyphens. Payloads are
1–131,072 printable non-space ASCII characters. Their format is opaque to this raw
layer; encryption and authentication are not inferred from those syntax checks.
`toString()` on the public value redacts all fields.

`retain` returns the existing namespace/ID record before checking capacity, even if
the candidate has different ciphertext or generation. Otherwise insertion and the
global count check occur in one transaction. A caller chooses a capacity of 1–128
records; a full store returns null and never evicts an input. Global capacity applies
across namespaces. At the maximum the payload bound is 16 MiB, excluding row overhead.
Concurrent connection owners observe committed retained values, rather than each
claiming their own candidate won.

`remove` compares the complete namespace, ID, generation and payload. Callers must
never reuse a generation for a new insertion: stale cleanup then cannot delete a
replacement after the original was removed. No tombstone remains after deletion;
duplicate callbacks and eventual business-command deduplication belong to the caller.
`page` returns 1–32 rows ordered by opaque ID, after an exclusive optional cursor.
It does not promise a snapshot across calls or timestamp-based fairness. A deleted
cursor still works; insertions before a cursor require another scan from the start.

SQLite schema13 adds `device_records` to schema12. IndexedDB schema12 adds the same
store to schema11. Neither migration rewrites existing account data. Account changes
and logout leave device records alone. Database ownership and closing remain with
the platform container. These records are covered by the application's existing
backup exclusions; they are not a cloud-backup format.

Raw storage propagates technical failures and cancellation. An interrupted operation
may have committed; recovery rereads or repeats the same retained identity. IndexedDB
mutations join their transaction completion after it starts. There is no automatic
retry, expiry, background job or notification callback in this component.

This foundation is ported from [HRIS Mobile76bd7673](https://github.com/fajarxfce/hris-mobile/commit/76bd7673e898b2c5ca4b6af98827a03848fc6071),
including the eight Apple storage cases accepted in full Mobile run41592237356 at81c7a35c.
Application encryption, notification policy, account transfer, protected keys and
background consumers remain in the consuming application. Starter does not opt a
feature into this storage automatically. Independent validation is recorded in
[port evidence](measurements/device-records-port-2026-11-03.json).
