# Atomic command archival and replica publication

`CommandStore.archiveAndMutate` retains one exact command/evidence pair in immutable storage and
publishes one or two prepared replica mutations under one account/session transaction. Command or
checkpoint/generation conflicts return false without writing. Constraint failure rolls back every
store, including replica writes preceding a failed final command deletion.

An exact archive replay succeeds without repeating the replica changes. Later progress remains
intact after an uncertain response. Inputs are validated and detached before I/O, including replay.
The caller owns encryption, completion policy, admission and scheduling. No new tables, retries or
event bus are introduced. Room explicitly refreshes invalidation after its outer writer transaction;
IndexedDB commits the existing command/archive/replica stores in one transaction.

Ten exact generic Mobile files and their platform contracts are ported here. Fifteen database
cases cover identity/CAS conflict, competing writes, cancellation, lost responses, observation,
reopening and forced archival failure. The independent full Starter gate passes 964 cases with
all 934 prior identities retained plus 30 JVM/browser executions. Android Dev/Staging/Prod builds,
lint, production Web, Apple source/test compilation and quality checks pass. Actual Apple runtime
execution is separate; this local gate does not claim it. Inspector remains pinned at 742a2d1c.
HRIS terminal publication and worker policy stay in Mobile.

Evidence: [docs/measurements/command-archive-replica-port-2026-11-13.json](measurements/command-archive-replica-port-2026-11-13.json).

## Atomic archival shared Apple proof linked

Mobile control 43427620202 at 42fd5d12c5353779d4a61cc50b8c53ae8c6c0bc6 passes 5,692 actual Apple tests,
including all 15 new atomic archival/replica cases. Ten generic source/test files remain byte
identical to this Starter port. The exact XML and successful job log are verified against the
downloaded artifact. This is Mobile execution of shared storage, not a separate Starter Apple
application run. See [evidence](measurements/command-archive-replica-shared-apple-2026-11-13.json).
The independent local 964-case Starter gate remains accepted. No new code or phone/live change.
Continue Mobile terminal publication and its finite local repair owner; do not rerun these gates.
