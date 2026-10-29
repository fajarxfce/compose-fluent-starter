# Agent continuation — Compose Fluent Starter

Updated 29 October 2026 (UTC). This repository is the reusable foundation. HRIS
Mobile was generated from it and is the proving ground: validate generic fixes
there before porting their implementation and portable regressions here. HRIS
business policy, backend adapters, routes and deployment configuration stay in
the consuming application.

## Start here

Read `AGENTS.md`, [architecture](architecture.md), [downstream development](downstream-development.md)
and [validation](validation.md). Inspect `git status`, recent commits and upstream
before edits. The current branch is `fix/push-token-lifecycle`; latest accepted
CI port `1817e45` follows Mobile proof. The validated application foundation remains
Firebase provider port `9a13791`; the new workflow changes no application sources.

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
those examples retain their specific scope and used Mobile application `02743a9f`.
Later Mobile `678783b3` corrects English Expense count copy, with root/Android checks. No generic implementation port was
needed for these HRIS fixtures; the Starter application remains unchanged since
`9a13791`, whose cross-platform CI passed.

The generic Apple CI correction is accepted. Mobile full run `40815794384` passed
Linux/Android, Web and Windows but reached the combined Apple compile/test deadline.
Its corrected run [40817592306](https://github.com/fajarxfce/hris-mobile/actions/runs/40817592306)
at `451a2c4d` passed before porting. Starter independently passed
[40820482699](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40820482699)
at `1817e45`: all 27 simulator tests in ten downloaded JUnit suites, complete
unsigned Xcode build, artifact upload and cleanup. Compilation took 6m28s, tests
47s and the Xcode step 8m31s. Local root checks passed in 21s with 306 desktop
cases, ten runner and six distribution regressions. Its project/scheme and three-job
default matrix are preserved. [Measurements](measurements/ci-2026-10-29.json) retain
source/destination proof and distinguish prior full CI from this Apple-only scope.

No reusable implementation port remains from this increment. Platform's corrected
full run [40820912608](https://github.com/fajarxfce/hris-platform/actions/runs/40820912608)
passed all ten jobs at `9345c19b`: four backend/dashboard shards each, actual
API/worker/export integration and schema-101 restore with a checked sanitized
artifact. Its prior upload and browser-locator failures remain in the Platform
measurement history. The owned temporary CI ref was removed after verification.
No CI or owned local test remains active. All native-owned resources are cleaned,
emulator data retained and location restored. Real FCM, Google exchange and
physical/iOS provider acceptance need their actual external input; build results
do not close those cases.

The user authorized continued work and pushing every atomic commit. Preserve
unrelated edits, omit co-author trailers and retain implementation/tests/docs
together. A Git push is not release or live-distribution authorization. Tracked
documents are the durable handoff; `.work` and chat history are supplementary.

## Validated ports

| Port | Source and destination | Recorded evidence |
| --- | --- | --- |
| Apple CI compilation budget | Mobile `451a2c4d` → Starter `1817e45` | Mobile run `40817592306` passed before Starter run `40820482699`; each retained 27 simulator tests and built the unsigned Xcode application. Separate 25/15/30-minute steps, 75-minute job bound, scoped manual dispatch and reports. |
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

This Apple CI port is accepted. Subsequent concrete downstream requirements must
be proved in Mobile, compared against source baselines and ported only when reusable.
Record deliberate naming/fixture differences and validate the Starter's real DI
containers; copying matching source alone does not prove integration.

## Commands

Use JDK 21, Python 3 and the Node/Android SDK versions in [development](development.md).
Root checks on headless Linux use `xvfb-run -a ./gradlew --no-daemon :check`.
The configured Android task is `:apps:android:assembleDevDebug`; run the affected
module's Chromium tests and other changed targets. Record local tests separately
from actual hosted CI and provider/device acceptance. Never claim CI success from
the presence of a workflow file.

For this workspace, supply tool locations explicitly (adjust paths on another host):

```sh
JAVA_HOME=/usr/lib/jvm/java-21-openjdk \
ANDROID_HOME=/home/fajar/Android/Sdk \
ANDROID_SDK_ROOT=/home/fajar/Android/Sdk \
CHROME_BIN=/home/fajar/.cache/ms-playwright/chromium-1248/chrome-linux64/chrome \
PATH=/usr/lib/jvm/java-21-openjdk/bin:$PATH \
xvfb-run -a ./gradlew --no-daemon :check
```

Append the affected browser/build targets to that command. Keep fixture-specific
API overrides local and explicit; this command preserves normal app configuration.

Update this file, downstream development and the relevant contract in each port's
commit, then push and verify the remote. For documentation-only continuation
updates, verify links and the recorded Git history; application tests need not be
repeated without a source change or unresolved concern.
