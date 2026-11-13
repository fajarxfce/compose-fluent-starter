# Atomic replica mutations

ReplicaStore.mutate applies one or two changes to distinct partitions under the same current
account. Each entry retains the snapshot/delta semantics and the200-document,4MiB bounds.
Checkpoint or generation conflicts and wake-capacity conflicts change neither partition nor
the optional recovery wake. A database constraint failure rolls back all writes. Mutable caller
collections are captured before suspension. Cancellation may lose a committed response but
cannot leave a partially committed pair; exact readback can recover the outcome.

Room reuses the existing writes inside one transaction after checking every condition.
IndexedDB replacement, delta commit and paired writes share one implementation. Tombstones,
tags, staging visibility and current account fences are unchanged. No migration is added.
Application policy, encryption and retry remain with callers.

The10 files match accepted Mobile0734dd82f6a017345e350c6d4de6d330b7539067 exactly. Fourteen new real storage
methods run on both desktop and browser, including conflicts, database rollback, ownership,
staging, mutable input, concurrent writers, cancellation, lost replies and durable restart.
Independent Starter verification retains all906 prior identities plus28 new executions,
totaling934. Android Dev/Staging/Prod builds, lint, production Web and iOS source/test compilation
pass. No new actual Apple execution, phone install or HRIS business logic is included.

Evidence: `docs/measurements/replica-atomic-mutations-port-2026-11-13.json`.
