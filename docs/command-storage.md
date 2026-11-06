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
transaction. Pages contain at most 50 records. Pending storage is limited to 128
records per account and 512 per database, with 128 KiB request and 32 KiB outcome
envelopes.
The [atomic batch operation](command-batches.md) publishes up to 128 commands
together, retaining immutable active/archive replay and these same capacity bounds.
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

`observeAll` observes the complete selected partition, bounded by the existing
128-command account capacity. It supports summaries that must include retained
operations beyond the ordinary 50-record page. It preserves account checks,
ordering, logout invalidation and cancellation; it does not schedule delivery or
classify outcomes. SQLite and IndexedDB share coverage through full capacity,
outcome changes, removal and replacement login.

## Immutable completion archive

`CommandStore.archive` atomically copies the exact observed command and opaque
completion evidence to `command_archives`, then removes its pending row. The
feature owns eligibility and encryption; the raw store cannot infer completion
from a response, age or state string. The operation fences current account/session
and the complete observed command in that same transaction: ID, resource, sort key,
request/state ciphertext and revision. An equal revision after delete/reinsert does
not make a replaced command eligible. A failed insert/delete rolls both
changes back. Evidence is bounded to 64 KiB of UTF-8; original request/state bounds
remain. An identical complete-record/evidence replay succeeds without replacing anything.
Changed proof, stale or replaced records and missing commands fail without mutation.

`archived` reads one exact account/partition/ID. Ordinary `get` and observations
continue to describe pending rows only. Archived identity remains immutable and
fences future `insert` in its database transaction. The same request/resource/sort
identity returns `AlreadyStored` even if pending is gone; archive-aware callers
then read `archived` and validate their decrypted identity. Different identity
cannot recreate that command. State updates and ordinary removal never change an
archive. A different operation ID may reuse the freed pending resource/sort slot.

The 128-account/512-database pending limits exclude archived rows. Archive reads
use the primary key; there is no unbounded archive scan or silent eviction.
`containsRecords` includes archives from inactive accounts so content keys cannot
regenerate over retained ciphertext. Logout keeps the completion evidence while
revoking access, and reauthentication binds the original account again.

SQLite schema 11 and IndexedDB version 10 add only the archive table/store. Migration
retains existing pending commands and account bindings. Shared contract tests cover
competing updates/archives/inserts, same-revision replacement, immutable replay, lost responses, cancellation,
account fences, pending capacity and byte bounds. Native/browser tests additionally
exercise reopening, the previous-schema upgrade, and an injected transaction abort
between archive creation and pending deletion.

This is a raw storage foundation. A feature must preserve and validate its complete
completion evidence before requesting archival. Receipt-journal cleanup, history
projection, local deletion and retention are separate feature policies; no scheduler
or automatic cleanup is introduced by these methods. Executed validation and the
Starter port are recorded in the [measurement](measurements/command-archive-2026-11-01.json)
and Git handoff. Both repositories pass their full local checks.

## Atomic handoff to a pending successor

`CommandStore.archiveAndInsert` retains the exact observed row and opaque evidence,
removes it from active work, and inserts one initial successor in a single transaction.
The successor must have another ID in the same account, partition and resource. Its ID
must be absent from both active rows and archives. Current account/session, the full
original row and normal size bounds are checked together. It reuses the occupied slot
at full queue capacity. There is no intermediate empty/partial observer snapshot.

Evidence can represent a local handoff rather than remote completion. The feature
owns that distinction, encryption and the relationship between the two operations.
A committed handoff returns true. Repeating it returns false, even with identical
arguments or after the successor has finished/disappeared. Callers explicitly read
and validate their retained link; the raw store never recreates missing work or
claims that an earlier remote request succeeded. Ordinary archive behavior is unchanged.

The exact eleven generic files were ported from Mobile7ef01cd5 after its actual
encrypted HRIS consumer passed focused and complete checks. Starter's root, database
browser, Android assembly/lint and production Web checks passed in244.326s on1,024
unchanged inputs:280 fresh cases/74 suites, zero failures/errors/skips. Android lint
has zero errors and44 existing warnings. Apple simulator acceptance remains pending.
[Evidence](measurements/command-handoff-port-2026-11-06.json). HRIS setup policy,
protocol, encrypted formats and owning use cases remain in Mobile; no schema changes.
