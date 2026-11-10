# Agent continuation — Compose Fluent Starter

Updated 10 November 2026, 10:49 UTC. This is the current checkpoint. Earlier detail
remains in [Git at6157db1](https://github.com/fajarxfce/compose-fluent-starter/blob/6157db1/docs/agent-handoff.md).

## Current pending-worker source — 10 November, 10:45 UTC

Mobile8c76f92c5c92cdf88d6827f3678e005c581fa8ab is committed/pushed17014 (REAPED):22 Kotlin inputs give the
registered worker an authenticated schema2 message phase in the existing schema1-compatible
partition, exact Instant/ID ordering and one durable claim. Old binding claims resume first;
new pending commands use owning preparation/staging/delivery. Failed attempts advance before
backoff; unready recipients return Unavailable instead of permanent Conflict. Concurrent
cancellation ends only its attempt, and only the dedicated Finish owner checks the complete
pending partition before wake completion. No usecase calls another usecase.
[Contract](https://github.com/fajarxfce/hris-mobile/blob/8c76f92c/docs/collaboration-space-pending-recovery.md),
[measurement](https://github.com/fajarxfce/hris-mobile/blob/8c76f92c/docs/measurements/collaboration-space-pending-recovery-2026-11-10.json).

Focused1/40918 REAPED FAILED156.483s:67 domain cases passed, then a cross-module test smart
cast failed compilation. Its20 source bytes and review are retained. Corrected focused2
finished435.576s:251 fresh cases,zero failures/errors/skips; its runner2452365 exited and
strict audit passed (wrapper session ID lost across compaction). Full1/19057 is REAPED PASS
581.55s:10010 verified =7991 fresh +2019 exact accepted reused,zero failures/errors/skips.
Root/Android host/release compile/lint/Wasm/iOS/actualChromium/proofAPK/unit/quality pass.
All4732 source/library inputs fixed,manifest
fad9f11cebc53e7e60444da4341dc9b18fca16d688872ecf2aceef1d0f21d920.
Strict focused/full auditors and publisher pass. ProofAPK150599939bytes,
SHA50cb5b5b249663df1168f58f60fce02fccbecc8da97edca8006a9a307db14766,compiled only.
No native or phone behavior claim. Core source freeze ended after acceptance; the separate
proof2/34855 compile is now the only heavy job (3GiB, no emulator).

Current native WIP is APPLIED:3 Mobile proof Kotlin inputs and4 Group test/driver inputs.
Core10010 remains unchanged. Private proof1 stopped before compilation because its copied
runner compared the obsolete baseline; no tests/compiler started. Corrected proof2/34855
is RUNNING and Mobile sources are FROZEN. Group test source is not compiled yet. All28 Python
cases pass, retaining18 original-delivery +4 prior recovery +6 new ordering/evidence cases.
An initial merged test run missed two imports (10 subtest errors); corrected before compile.
Do not overwrite prior cases with the older24-case subset draft. Group/native runners and
exact gates are under .work/space-pending-native-2026-11-10 in Mobile/Group.

NEXT: finish/audit Mobile proof2, compile Group tests, then invoke four actual native cases.
New case queues an unbound Space command; real worker claims/stages/delivers it beside two
unchanged Company originals, interrupts receipt projection and recovers after process loss.
Driver forwards listening/control before waiting for staging; retained app-count evidence
is distinct from direct SDK inventory. Preserve original-only/mixed/group controls. Actual
native four cases NOT RUN. Never inspect SDK while staging lease is held. No phone build.

Host controls:42796816133 on1d18e232 has audited7930 portable (90228 REAPED),artifact13756830729,
SHA97d61d4309bc4a6716c38b949a2e9c5d20945b1c02a45f54d74cfe6576cf0e37,
report manifesta0396dc8939ec3b1d7e69f0892264a744c4013a84a398a51ca7f2bbc1e86d5f0;
Apple3028 pending host completion. [Partial hosted evidence](https://github.com/fajarxfce/hris-mobile/blob/8c76f92c/docs/measurements/collaboration-mixed-recovery-demand-hosted-2026-11-10.json).
Pending-inventory42792315555 COMPLETE10862=7866portable+2996Apple,16Swift/unsignedhost.
Apple87521 is REAPED PASS2996/306suites,artifact13756512123,
SHA0aaa2c1e87f6aaa1e891e8f6ff5199f9bdee5ab1ec8d95431adcaec1077dd31e,
manifest8d3b847f0d6992894ff983bbf0bbddedf7b7d5f50f27f8b550469cd6052ebba4.
Source/digest/CRC/predecessor identities verified. Complete measurement accompanies this checkpoint.
New worker control42801929551 is RUNNING on8c76f92c,validation/space-pending-recovery-20261110;
ref61718/dispatch14028 REAPED. Expected8008 portable +3062Apple=11070 +16Swift/host.
Private hosted config and predecessor-case auditor are complete; do not redispatch.

Phone87 last known;88 uninstalled;ADB34894 timeout15s at10:39 REAPED,availability question
pending. Phone remains install-only; announce installations,never drive/clear UI. Live
 e558a686/schema116.4/readinessON/groupOFF unchanged. Starter generic sourcea7ff417c remains
accepted358 fresh; keep HRIS policy here. Actual Apple Matrix runtime remains unavailable.
All54 PRD groups remain open. Continue native acceptance, incoming/history/edit/product
routing, media, backup, calling and meetings. No new phone build yet.


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
