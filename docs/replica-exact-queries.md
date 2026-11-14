# Exact published replica queries

This independent port follows Mobile `d889a9afdc3b3ebaa264222a0aca2a0775ab5443`. `ReplicaQuery(id = ..., limit = 1)` selects a live document by primary-key identity beside other bounded indexed pages under one published checkpoint. Exact identity is independent of sort order and cannot be combined with tags or cursors. SQLite and IndexedDB preserve account checks, published generations, tombstone exclusion and captured caller input. No schema migration, new observer or application policy is introduced.

The four new contracts per backend cover exact/indexed selection, deletion/revival, coherent staging publication, account replacement, cancellation, malformed selectors, legacy serialized inputs, caller mutation and database reopen. The Apple wrappers compile against the same contract; Apple runtime acceptance remains separate.

Validation: 1,060 cases (512 fresh / 548 exact unchanged), preserving all 1,052 prior case identities plus 8. Full root checks, three Android variants, Android lint, production Web, browser storage/application tests, Apple device source and simulator test-source compilation passed. No new APK installation or startup screenshot is claimed for this raw database change. Existing resource/startup acceptance remains independently recorded.

[Exact validation](measurements/replica-exact-queries-port-2026-11-14.json).
