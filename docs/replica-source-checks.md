# Exact source guards for replica publication

ReplicaMutation.Check compares an exact working checkpoint, including confirmed absence, in
the same transaction as a destination write. It preserves source revision and ciphertext.
One or two distinct partitions and at least one actual write remain required. All predicates
are checked before documents or a wake change. SQLite and IndexedDB retain atomic rollback,
account scope, cancellation and completed command-archive replay semantics.

Eight exact generic source/test files follow the accepted Mobile consumer. Thirteen new shared
actual-storage methods cover destination observation, source/absence/staging checks, ordering,
invalid/foreign inputs, rollback, wake conflicts, competing writers, cancellation, lost replies,
archive replay and reopening. Independent Starter validation passes 990 cases, preserving all
964 prior identities plus 26 desktop/browser executions. Android Dev/Staging/Prod, lint, Web
execution/distribution and Apple source/test compilation pass. Actual new Apple execution is
separate; no independent Starter Apple application runtime is claimed.

Use cases and repositories own admission, encryption, content projection and recovery policy.
There is no new polling queue, HRIS feature, native transport activation or database schema.
See docs/measurements/replica-source-checks-port-2026-11-13.json for exact-source evidence.

Actual shared Apple storage execution is accepted in Mobile control 43467846733, exact source
`951c308dce2dd350a901c67cf449d6913b97cacf`. All 13 source-guard methods pass; the eight ported files are
byte-identical in that source and Starter. This does not claim an independent Starter Apple
application run. Evidence: `docs/measurements/replica-source-checks-shared-apple-2026-11-13.json`. The accepted independent local 990-case gate is unchanged.
