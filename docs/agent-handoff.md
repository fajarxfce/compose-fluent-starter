# Agent continuation — Compose Fluent Starter

Updated 10 November 2026, 10:33 UTC. This is the current checkpoint. Earlier detail
remains in [Git at6157db1](https://github.com/fajarxfce/compose-fluent-starter/blob/6157db1/docs/agent-handoff.md).

## Current worker checkpoint — 10 November, 10:33 UTC

Last accepted Mobile product is [1d18e232](https://github.com/fajarxfce/hris-mobile/commit/1d18e2326cbb286630f92cb29d934b6b80d79d59), committed/pushed.
Mixed/new-room demand focused1/77984 and full1/2861 are REAPED PASS:162 focused,
9932 verified full (7913 fresh +2019 exact accepted reused), zero failure/error/skip.
All4726 source/library inputs stayed fixed; manifest
12b6aedbe631cb22bfe623b5b708057291ba0169922932af56e6930e418a4047.
Control42796816133 remains active on that exact source, expected7930 portable +3028
Apple. Pending-inventory42792315555 has audited7866 portable; Apple shared checks passed,
unsigned host is still building. Mixed-core42785697360 is complete10808 +16Swift/host.
Do not rerun accepted local gates or redispatch these controls.

Current pending-worker candidate has22 APPLIED Kotlin inputs, not yet accepted. It adds
an authenticated schema2 message phase in the existing schema1-compatible partition,
exact Instant/ID ordering and one durable claim; resumes old binding claims, stages pending
Space commands through owning usecases, advances failed attempts before backoff, and checks
the complete pending partition before finishing the captured wake. Generic UpdateProgress
rejects unchecked Finish. No usecase calls another usecase. Stage now classifies an unready
recipient as Unavailable instead of permanent Conflict; a cancellation returned by Stage
ends the attempt without delivery. Source1d18e232 remains the accepted baseline.

Focused1/40918 is REAPED FAILED156.483s:67 domain Desktop cases passed, then a public
nullable-property smart cast failed test compilation. Those20 candidate source bytes are
retained privately. After reaping, the smart cast and owning recipient classification were
fixed and cancellation/unready regressions added. Focused2 is currently running, expected251
fresh cases (94 domain per Desktop/Chromium,18 storage per platform,25 worker +2DI), one3GiB
compiler, no emulator. Its wrapper session ID was lost during compaction; runner PID2452365
and .work/space-pending-recovery-2026-11-10/focused-2/result.json identify this run. Freeze
all4732 source inputs until the runner finishes. Strict full audit expects10010 if focused
passes; full has not started. Private README/runners supplement this tracked checkpoint.

NEXT: complete focused/required full gates, then add actual native pending-worker coverage
beside the accepted original-only/mixed/group controls. Queue a new Space command without
an initial local binding; let the registered worker prepare/stage/deliver, interrupt after
an original receipt boundary and recover exact transactions after process loss. The prior
mixed native proof used direct delivery, so it does not establish this new worker path.
[Tracked plan](https://github.com/fajarxfce/hris-mobile/blob/feat/chat-send-timing-20261107/docs/collaboration-space-pending-recovery-plan.md).
No native proof/backend implementation for the new case yet. No phone/live changes:
phone87 last known,88 uninstalled; ADB76351 timeout15s at10:10 is reaped, question pending.
Phone remains install-only; announce installations, never drive/clear UI. Livee558a686 /
schema116.4 /readinessON /groupOFF unchanged. Starter generic sourcea7ff417c remains accepted358
fresh; do not port HRIS business policy. Actual Apple Matrix runtime is still unavailable.
All54 PRD groups remain open; continue incoming/history/edit routing,media,backup,calls/meetings.


## Authorization and scope

The user authorized long-running HRIS delivery, atomic commits/pushes and validated
phone updates. Mobile proves reusable changes before they return here. Keep HRIS
policy, API adapters and configuration in Mobile. Follow AGENTS.md and the owning
layer contracts. All54 communication PRD groups remain open; this port does not
complete one. One heavy local compiler OR emulator at a time; preserve unrelated
files and do not kill the live API/worker JVMs.

## Pinned inspector workflows accepted

Five jobs across CI and Android/iOS distribution now check out public inspector
742a2d1c2285c720cef80ea3913195f3ece388cc without persisted dependency credentials.
Nine shared SBOM artifacts retain the exact officially verified Mobile checksums.
The prerequisite Mobile verify42611795210 passed7,950 cases, zero failures/errors/
skips/lint; artifact13672363171 was independently audited. Root Mobile integrated
these workflow/checksum/fixture changes as57ebc77a after66 focused cases passed.

Starter's four workflow/checksum files match the reviewed before/after hashes.
Full88319 is REAPED PASS188.590s: root checks, all three configured Android builds,
lint, actual browser tests, production web, iOS source, strict SBOM and quality.
62 fresh browser cases/15 suites pass with zero failures/errors/skips; cached
Desktop reports are not counted again. Inputs1,052/library49 remain fixed. Lint
has0 errors and45 existing dependency advisories; the SBOM has366 components.
The three APKs and web distribution inventory are hashed in the evidence.
[Exact port and verification](measurements/pinned-inspector-ci-port-2026-11-09.json).

The local dependency worktree is
`/home/fajar/hris/.worktrees/inspector-pinned-consumer-2026-11-09`, detached at742a2d1.
All85 tracked files match committed bytes. A fresh checkout reports gradlew.bat
modified because tracked CRLF bytes pass through its text/eol normalizer; the
first clean-status preflight stopped before any build. No file/attribute/index
was reset or changed. Preserve the original inspector worktree's separate delta.
No Starter hosted CI, distribution dispatch, release or phone install occurred.
Apple native Matrix binding and runtime acceptance remain separate open work.

## Prior generic ports

Session maintenance and scheduled/requested worker triggers remain accepted in
6157db1, following Mobile's measured parent renewal and request ownership fixes.
Atomic command observations, bounded replica queries and local timing labels are
already ported. Their exact evidence remains under docs/measurements; do not
reapply old patches or follow historical notes about already-reaped jobs.

## Active: generic replica and recovery wake transaction

Mobile7a6fe534fc2e86064be9f60509f7befcc6afbf31 is accepted across its required gates.
Hosted42749839268 passed9,985 tests (7,276 portable,2,709 Apple),16 Swift checks and
unsigned iOS host. Audits20231/77499 are reaped; exact artifacts/source/predecessor
case identities verified. Local evidence and stale generated-delegate diagnosis
remain in Mobile docs/measurements/replica-work-wakes-2026-11-10.json.

The exact nine generic core files from that Mobile commit are applied here after
its acceptance. All bytes match Mobile Git; no HRIS policy/configuration was copied.
This version uses ReplicaWakeRequest. The newer command WorkWakeRequest API is
separate and must await its own platform acceptance before a later port.
Private .work/replica-commit-wake-port-2026-11-10 retains patch,per-file hashes,
runner and accepted.json. Full61949 REAPED PASS588.178s,332 fresh tests/85 suites,
zero failures/errors/skips. The new eleven SQLite and eleven actual Chromium cases
are included. Root,all three Android builds,lint,production web,iOS source,strict
SBOM and quality pass. Inputs1,057/library49 remained identical. Lint has0errors and
45 existing dependency advisories; exact APK/web/SBOM hashes are recorded in
[accepted evidence](measurements/replica-work-wake-port-2026-11-10.json).
The nine core files still match accepted Mobile Git bytes. Earlier metadata-key
preflight stopped before compilation and required only private runner correction.
No Starter compiler remains. No phone install,live change or distribution dispatch.

Mobile sourceb9db0bd9 is accepted/pushed:focused68,full9,418 (7,399 fresh+2,019 exact
reused),zero failures/errors/skips. Source4,677/library and all report identities were
verified;90 AppFlow cases passed unchanged. Its immutable control42763070460 remains
running,expecting7,416 portable+2,779 Apple and16Swift/unsignedhost.

Producer42753380451 is now fully accepted:10,009 tests including2,717 Apple,16Swift
and unsignedhost; audits29517/71981 are reaped. Command42758828055 is also fully
accepted:10,048 tests including2,730 Apple,16Swift/unsignedhost; audits93083/9004 are
reaped. Exact source,digests,ZIP CRC and predecessor case identities passed. Mobile
fbbf96a2 publishes complete command proof. Do not redispatch the remaining message job.

After that full Mobile command proof,the exact15-file generic database patch from
b1adfd820e5e560bf3d689a6bee09a88d1f67a9a is APPLIED here at05:57:19 UTC but remains
locally ACCEPTED for the source/docs commit. WorkWakeRequest replaces ReplicaWakeRequest;
CommandStore.insertBatchAndWake atomically retains new commands and work,while replay
of retained active/archive IDs creates no wake. Thirteen new shared database cases
have Desktop/Chromium/Apple wrappers. No HRIS policy,configuration or document fixture
was copied. Private .work/command-work-wake-port-2026-11-10/port.json and apply.py
verify both Mobile hosted audits,all pre/post Git bytes and the removed rename source.
Patch SHA72622f3e6ef58712089ab81d223d538102bf2cdd9840e08574dddda33995f164.

Independent full-1/session32816 is REAPED PASS518.812s:358 fresh tests/87 suites,
zero failures/errors/skips. New13 SQLite/13 Chromium command cases and retained11+11
replica cases pass. Root,all three Android builds,lint,production web,iOS source,
strict SBOM and quality pass. Inputs1,061/library49 are unchanged; source manifest
ad90ba02474fa5a34be79ca5268e6aa7b289a9e00b55cb8ac5d5414c5776690d;
report manifestd7630e3373636dd2318a0c3a095fe9cd2d91248810a677d7fc50b6fc0a525c32.
Strict accept.py verifies all exact source and XML bytes. Lint0errors/45 existing
advisories; SBOM366 components,three APKs and26 web files are hashed. Its fifteen
source files match Mobileb1adfd82; the old rename source is absent.
[Accepted port evidence](measurements/command-work-wake-port-2026-11-10.json).
No Starter compiler remains. Commit/push this accepted port with its evidence.

Mobile receipt sourcedb4fc4f0 is accepted/pushed:focused62,full9,488 (7,469 fresh+
2,019 exact reused),zero failures/errors/skips; source4,689 unchanged. Control
42766554589 is running at that immutable source,expecting7,486 portable+2,814 Apple,
16Swift/unsignedhost. Message42763070460 portable7,416 is accepted; Apple is building
the unsigned host. Producer42753380451 and command42758828055 are fully accepted.

Mobile now has uncommitted typed mixed queue policy/reader,owning-module DI and34
domain cases. Focused-1/session19427 is RUNNING as the only heavy local compiler.
Freeze Mobile source; no emulator/second compiler. Reader checks both ledgers and
parent/admission at each I/O boundary,then verifies final binding/native inventory.
No native resume,settlement,enqueue,worker or UI routing is enabled by this reader.
Mobile docs/agent-handoff.md owns current jobs and next steps.

Phone87 unchanged,release88 uninstalled; latest ADB8250 timed out15s around05:56,
question already pending. Live remains e558a686/schema116.4/readinessON/groupOFF.
Continue all54 open PRD acceptance groups after this checkpoint.
