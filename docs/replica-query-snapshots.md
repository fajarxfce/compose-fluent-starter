# Bounded replica query snapshots

One owned replica transaction accepts at most four query windows, each requesting at most
200 records and at most 400 records in total. The aggregate budget accommodates two bounded
inventories without combining different committed revisions. Ownership, partition and
checkpoint checks remain unchanged; there is no schema or API migration.

The shared SQLite/IndexedDB contract reads two 129-record indexes in one snapshot, accepts
an aggregate budget of 400, and rejects 401, individual windows over 200 and five windows.
This exact two-file port follows Mobile a2e040a0 acceptance. The complete Starter gate passed
236 fresh cases, including five actual Chrome replica tests, plus Android assembly/lint,
production web, iOS source and root quality checks. Native Apple execution is not claimed.
[Exact command, fingerprints and reports](measurements/replica-query-port-2026-11-08.json).
