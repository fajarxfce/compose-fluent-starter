# Agent continuation — Compose Fluent Starter

Updated 29 October 2026 (UTC). This repository is the reusable foundation. HRIS
Mobile was generated from it and is the proving ground: validate generic fixes
there before porting their implementation and portable regressions here. HRIS
business policy, backend adapters, routes and deployment configuration stay in
the consuming application.

## Start here

Read `AGENTS.md`, [architecture](architecture.md), [downstream development](downstream-development.md)
and [validation](validation.md). Inspect `git status`, recent commits and upstream
before edits. The current branch is `fix/push-token-lifecycle`; the last accepted
checkpoint is `a16325e`, documenting the validated Firebase provider port `9a13791`.

The local siblings are `/home/fajar/hris/hris-mobile` (`feat/native-push`) and
`/home/fajar/hris/hris-platform` (`feat/firebase-auth`). Their tracked continuation
pages are [Mobile](https://github.com/fajarxfce/hris-mobile/blob/feat/native-push/docs/agent-handoff.md)
and [Platform](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/agent-handoff.md).
The overall HRIS PRD remains in progress. Platform `b7f42070` integrates validated
push-delivery observations (`409abad4`), original payroll comparison (`f757bffd`)
and actual one-hour export retirement (`1abdbce9`). Subsequent native fixtures
`578a5031` (Overtime), `97e33310` (Expense), `ccae6f8f` (Leave) and `a837320f`
(Attendance) pass real API/Android response-loss, offline restart and exact replay.
The six Mobile pending-action observers now each have a native recovery example;
those examples retain their specific scope. Application sources remain `02743a9f`.
Mobile `ad675e57` records the latest evidence. No generic implementation port was
needed for these HRIS fixtures; the Starter application remains unchanged since
`9a13791`, whose cross-platform CI passed.

Current continuation is complete CI/PRD reconciliation. Platform `d7184e83` adds
manual Verify dispatch, now running as
[40816036721](https://github.com/fajarxfce/hris-platform/actions/runs/40816036721).
Mobile CI at `b0a95391` (same application sources) is running as
[40815794384](https://github.com/fajarxfce/hris-mobile/actions/runs/40815794384),
covering Linux/Android/Web, Windows and Apple simulator. Inspect their current
status in the respective handoffs before claiming completion. No release or
physical/provider acceptance is implied. All native-owned local resources are
cleaned, emulator data retained and location restored.

The user authorized continued work and pushing every atomic commit. Preserve
unrelated edits, omit co-author trailers and retain implementation/tests/docs
together. A Git push is not release or live-distribution authorization. Tracked
documents are the durable handoff; `.work` and chat history are supplementary.

## Validated ports

| Port | Source and destination | Recorded evidence |
| --- | --- | --- |
| Firebase provider ownership | Mobile `d191c61d` → Starter `9a13791` | 20 provider source/test files, host wiring and two actual container regressions; 306 desktop/UI and 29 identity-data Chromium tests, configured Android assembly. CI run [40648662419](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40648662419) passed Linux/Android/Web, Windows and Apple simulator/Xcode. |
| Notification resource paths | Mobile `b77d991f` → Starter `7562e67` | Four source/test files, 291 desktop/UI and eight notification-data Chromium cases, Android assembly and cross-platform CI recorded at `da0855b`. |
| Provider token retirement | Mobile `e56ef32b` → Starter `9cb83a2` | Thirteen matching source/test files; current-token observation, SDK deletion and cancellation/late-result coverage. Use the downstream ledger for exact validation. |
| Incoming link ownership | Starter `29081c5` from validated Mobile ownership changes | Session-bound pending destinations, matching acknowledgement, resumed-host entry; root, app Chromium and Android checks in both repositories. |

Consult the complete downstream ledger before applying or repeating a port; it
also records replica storage, file ownership, navigation and UI foundations.

## Provider and next-work boundaries

The Firebase provider returns an opaque proof and never publishes a consuming
application's session by itself. HRIS owns project discovery, binding, native
exchange, MFA and login policy. Actual Firebase email/password through HRIS was
accepted on Android; Google chooser/Back was accepted without a Google ID-token
exchange. Those facts do not prove generic provider delivery on every target.

Local ignored Android config uses project `cmp-fajar`. Real FCM delivery requires
the consuming server credentials and device registration; iOS requires its own
Firebase/APNs/device setup. No service account, private key or app-specific
configuration should enter a generic port. Recheck local configuration and the
Mobile handoff before requesting previously supplied input.

There is no outstanding reusable implementation identified by the latest accepted
Mobile/Platform increments. Continue the next concrete downstream requirement,
prove it in Mobile, compare source baselines and then port only the reusable part.
Record deliberate naming/fixture differences and validate the Starter's real DI
containers; copying matching source alone does not prove integration.

## Commands

Use JDK 21, Python 3 and the Node/Android SDK versions in [development](development.md).
Root checks on headless Linux use `xvfb-run -a ./gradlew --no-daemon :check`.
The configured Android task is `:apps:android:assembleDevDebug`; run the affected
module's Chromium tests and other changed targets. Record local tests separately
from actual hosted CI and provider/device acceptance. Never claim CI success from
the presence of a workflow file.

Update this file, downstream development and the relevant contract in each port's
commit, then push and verify the remote. For documentation-only continuation
updates, verify links and the recorded Git history; application tests need not be
repeated without a source change or unresolved concern.
