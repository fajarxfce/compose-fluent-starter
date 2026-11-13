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
