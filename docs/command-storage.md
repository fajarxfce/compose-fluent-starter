# Durable command storage

Content-key creation can use the [atomic credential port](protected-credentials.md).
The raw database does not create or recover cryptographic keys itself. The adapter
and its regressions are shared with [HRIS Mobile](https://github.com/fajarxfce/hris-mobile/commit/291a64c).

`AppDatabase.commands` retains encrypted requests independently of disposable
replicas. Logout and a replacement session revoke access without deleting an
unconfirmed operation. Integrating applications bind the active session to a stable
API-audience/account key through `accounts.activate(sessionId, accountKey)`.
A later authenticated session for that same account can recover its records;
another account cannot read or update them. The template's demo session leaves this
binding absent; its saved-activity outbox remains a separate disposable fixture.
This local binding does not grant server permission or offline company admission.

The raw store performs database I/O only. Features encrypt request and outcome
envelopes, map storage results, and own command eligibility, retry, authorization,
conflict review and retention. No HTTP operation starts from a storage callback.
Feature integration is separate from this storage capability.

Each partition retains one operation per resource. The operation ID, resource,
ordering key and request ciphertext are immutable. Updates change only the outcome
envelope and compare its observed revision atomically. Removal also requires that
revision. Repeating an identical insertion recovers the existing operation without
overwriting a later outcome. An operation cannot silently replace another request.

Every scoped query and write checks the current session/account in the same
transaction. Pages contain at most 50 records. Storage is limited to 128 records per
account and 512 per database, with 128 KiB request and 32 KiB outcome envelopes.
Cancellation leaves an absent or complete transaction. A committed browser write
still notifies observers if its caller is cancelled. Owners close database resources.

SQLite and IndexedDB contract tests cover competing writers, lost local responses,
immutable requests, account/company isolation, logout/re-login, bounded capacity,
cancellation, migration and reopening. Encryption, backend receipts, UI actions,
and physical-device behavior require their own integration checks.

Validation: the Linux workspace check, Android dev debug build, and actual SQLite
and IndexedDB tests passed. This storage was integrated from
[HRIS Mobile](https://github.com/fajarxfce/hris-mobile/commit/075b95a); its HRIS
session binding and business command policy remain in that application.
