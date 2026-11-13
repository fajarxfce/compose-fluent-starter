# Incremental local replica pages

ReplicaStore.changedDocuments reads bounded coalesced published changes, including raw tombstones.
Each raw record retains an internal local changedAtRevision independent of the caller's document
version. Replacement records start at zero; effective commits assign the new checkpoint revision.
Exact replay, ignored old versions and prevented resurrection do not create row changes.

One SQLite/IndexedDB transaction owns account, checkpoint, records and lookahead. A composite index
seeks directly by owner/partition/generation/local revision/ID, with at most200 records plus one
lookahead. After starts an exclusive-lower/inclusive-upper interval; Within continues its finite
upper bound. Newer overwrites belong to the next interval. This is not a historical payload view.
Staging does not hide the active generation; generation replacement returns Restart. A raw cursor
cannot silently cross login scope. The caller must confirm publication or durable handoff before
persisting completion through the window's upper revision; raw exhaustion is not business success.

SQLite13->14 and IndexedDB12->13 preserve existing payload bytes, source versions, sort keys, tags,
tombstones, active/staged checkpoints and subsequent change detection. Migration tests include
SQLite13, browser12 and the asynchronous account-ownership copies from browser7. Twelve shared
methods cover paging, equal source versions, replay, deletes, concurrent intervals, staging,
ownership, bounds, conditional rollback, competing writes, cancellation and close/reopen.

Twenty-one exact Mobile code/schema/test files pass independent Starter1049: all1022 previous cases
plus13 desktop and14 browser executions. Android Dev/Staging/Prod, lint, Web and Apple source/test
compilation pass. Actual new Apple execution remains separate. Inspector742a2d1c stays pinned.
There is no transport, domain permission, retry scheduler or HRIS policy in this raw storage API.
See docs/measurements/replica-local-change-pages-port-2026-11-13.json for exact-source evidence.
