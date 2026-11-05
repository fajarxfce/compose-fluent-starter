# Atomic command batches

`CommandStore.insertBatch` commits a bounded list of opaque commands in one
transaction. All commands use the current session/account binding; partitions can
differ. The caller supplies immutable operation IDs, request ciphertext and initial
state. The store does not encrypt, authorize a feature action or schedule delivery.

The method detaches the input list before suspension and accepts 1–128 records.
IDs, resource IDs and sort keys must be unique within each partition. Existing
128-command account and 512-command database limits count only missing active
records; an exact replay remains valid when capacity is full.

An identical active or archived request returns `AlreadyStored` when the complete
batch is already retained. Adding missing records returns `Stored` without replacing
existing state, revisions or archives. Request/resource/sort conflicts fail without
writing a partial batch. An occupied resource or full queue also leaves the entire
batch unchanged. Unique-index failures roll back earlier writes in the transaction.

Session replacement rejects old writers while allowing an admitted new session for
the same account to recover the original commands. Cancellation leaves either no
publication or the complete batch. Consumers must preserve operation IDs across an
uncertain response and authenticate any recovered application envelopes. Randomized
encryption and logical replay belong to a feature repository, outside the raw store.

The ten source/test files match HRIS Mobile
[`e3c1e326`](https://github.com/fajarxfce/hris-mobile/commit/e3c1e3262e8a306ea587f73de8bfa974dd54188c).
Mobile's Forward consumer additionally proved an offline publication to two chats,
cold restart, delivery after reconnect and actual encrypted receipt on both Android
receivers. That application metadata, encryption and UI stay in Mobile.

Six shared cases run on SQLite, IndexedDB and Apple wrappers: immutable replay and
archives, complete rollback, ownership and bounds, capacity, competing writers and
cancellation, and caller-list mutation. The replay case also closes and reopens the
database with a replacement session. No schema migration is needed.

Independent Starter root check, database browser tests, Android dev/lint and Web
build pass in 803.314 seconds. Reports contain 383 desktop, 59 browser and six
build-logic cases; 243 cases ran freshly, including all six batch cases on both
SQLite and IndexedDB. No failures/errors/skips; lint has zero errors and 44 existing
warnings. [Evidence](measurements/command-batch-port-2026-11-05.json) retains source
hashes, task provenance and report hashes. Independent Starter Apple remains pending.
