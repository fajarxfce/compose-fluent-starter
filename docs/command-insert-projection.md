# Atomic command and checked projection insertion

CommandStore.insertAndMutate retains one command, prepared replica changes and a fresh recovery
wake in one account transaction. An exact source check can guard the projection without changing
source bytes. Conflicts retain nothing; constraint failures roll back command, replicas and wake.
Active or archived identical command replay preserves later state/projection and creates no wake.
AlreadyStored proves immutable command retention only; callers authenticate their own original
handoff evidence before inferring that a projection was published.

Nine exact generic files follow the accepted Mobile consumer. Fifteen shared storage methods and
two extra SQLite fault injections cover commit, observers, source/index/wake conflicts, resource
and capacity limits, immutable replay, competing writes, cancellation, lost replies and restart.
Independent Starter validation passes 1,022 cases: all 990 previous identities plus 32 executions.
Android Dev/Staging/Prod, lint, Web execution/distribution and Apple source/test compilation pass.
Actual Apple runtime evidence remains separate. Inspector 742a2d1c stays pinned.

No schema migration, HRIS content policy, scheduler or native transport is introduced. Application
use cases own admission, bounded reconciliation and semantic projection; storage owns atomic I/O.
See docs/measurements/command-insert-projection-port-2026-11-13.json for exact-source evidence.

Actual shared Apple storage execution is accepted in Mobile control 43498640097, exact source
`b4a3a89d105f4f79032ced3c912c1d1f6a4a15d7`. All 15 atomic command/projection methods pass; the nine ported files are
byte-identical in that source and Starter. This does not claim an independent Starter Apple
application run. Evidence: `docs/measurements/command-insert-projection-shared-apple-2026-11-13.json`. The accepted independent local 1022-case gate is unchanged.
