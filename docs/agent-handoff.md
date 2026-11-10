# Agent continuation — Compose Fluent Starter

Updated 10 November 2026, 08:23 UTC. This is the current checkpoint. Earlier detail
remains in [Git at6157db1](https://github.com/fajarxfce/compose-fluent-starter/blob/6157db1/docs/agent-handoff.md).

## Mobile checkpoint — 10 November, 08:23 UTC

Mobile [507224a3](https://github.com/fajarxfce/hris-mobile/commit/507224a3c3e4cb4c87cc3a7ba8b4f8be9535bd54)
is committed/pushed (46472 reaped). Owning Space staging persists the original Staging
attempt before paused enqueue and requires exact CAS readback. It recovers the same physical
transaction after response loss; other unresolved same-device staging blocks enqueue.
[Contract](https://github.com/fajarxfce/hris-mobile/blob/507224a3/docs/collaboration-space-message-staging.md).

Focused-2/67472 PASS70/330.446s and full-1/14508 PASS664.833s are reaped and audited:
9,744 verified = 7,725 fresh + 2,019 exact accepted reused, zero failure/error/skip. All
required local platform/build/quality gates pass. 4,712 source/library inputs fixed;
manifest 81e08d95924901a1c1fed27bb96486d1ef2fc8692035cf74094ebc8af0566a8f.
Initial LongMethod failure and its invocation-phase repair remain recorded. Pinned control
42781554618 is running (ref14911/dispatch44288 reaped), expected7,742 portable+2,934 Apple,
16 Swift and unsigned host. Domain raw enqueue remains synthetic; native proof is next.

Eleven APPLIED, UNCOMMITTED/FROZEN Mobile inputs extend the EXISTING delivery window to
separate Company/Space ledgers, whole-inventory/proof planning before CAS, cross-ledger
transaction/event ownership, scoped late callbacks and one bounded missing-receipt history
window. New rooms perform no company command I/O. Focused-1/85670 is the ONLY local heavy
compiler, expected226 fresh (13 policy+26 new owning+73 existing domain cases/platform,
2 DI). No acceptance yet. Mobile .work/mixed-original-delivery-2026-11-10 retains drafts,
runners and integration notes; tracked Mobile sources are canonical after application.
[Current Mobile handoff](https://github.com/fajarxfce/hris-mobile/blob/feat/chat-send-timing-20261107/docs/agent-handoff.md)
owns further state. Actual native staging/delivery, complete pending inventory, incoming
history/routing, media, backups, calls and meetings remain. All54 PRD groups are open.

New-room0aaaf786 local9,676 is accepted;42777934754 portable7,674 is audited (61280 reaped),
Apple pending. Capability31a0ed9a local9,650;42775719615 portable7,648 audited, Apple host
pending. Projection42772661276 COMPLETE10,513+16Swift/host is published507224a3. Mixed
42769825037 COMPLETE10,402+16Swift/host is published0aaaf786. Preserve controls; do not rerun
accepted gates. Actual Apple Matrix runtime binding remains unavailable; hosted domain/
storage tests and unsigned host do not implement native Apple chat.

Starter a7ff417c remains accepted/pushed:15 exact generic command-wake files,358 fresh/all
required gates; no source WIP/compiler. HRIS policies stay Mobile. Phone87 unchanged,88
uninstalled; ADB7150 timed out15s at08:22 and is reaped. Existing availability question is
pending. Announce install-only updates; never drive/clear phone UI. No emulator. Live
e558a686/schema116.4/readiness ON/group OFF unchanged; preserve live JVMs.

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
