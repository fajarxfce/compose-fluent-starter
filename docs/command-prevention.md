# Permanently reserving an absent command

`CommandStore.archiveAbsent(scope, record, evidence)` records an immutable outcome
for an operation that must never enter the active command queue. The transaction
checks the current account/session, command ID and occupied resource before writing
the existing archive table. It never inserts a temporary Queued row. No schema
migration is needed.

An existing archive succeeds only when its complete record and evidence match.
An active command or resource conflict returns false and remains untouched. A full
active queue does not prevent archiving an otherwise absent operation. Retrying an
uncertain response reads the same immutable result; later queue insertion cannot
reuse the consumed ID. Room and IndexedDB enforce these decisions transactionally.
Features still own encryption, operation admission and the meaning of the evidence.

This port copies eight generic files from Mobile commit
`8b5f87501d618ac40fdcaccc94d4d97b85270e00`, where the Saved replies consumer proves
discard followed by restart and a duplicate callback cannot enqueue the discarded
message. HRIS identity, notification policy and UI stay in Mobile.

Independent Starter root checks, Android dev/lint and production Web passed in
253.982s:367 desktop,53 actual browser and six build-logic cases, zero failures.
The new desktop and browser contracts executed freshly.
[Evidence](measurements/command-prevention-port-2026-11-03.json) records source and
report hashes, cache reuse and the44 existing lint warnings. Full hosted Apple
remains pending; see [the handoff](agent-handoff.md) for execution state.
