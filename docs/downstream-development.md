# Downstream development

## Apple Google authorization-code ownership

Mobile `43cd8ec6` passed all four jobs in full
[run 40858176601](https://github.com/fajarxfce/hris-mobile/actions/runs/40858176601)
before porting: 97 simulator cases in 26 downloaded JUnit suites, no failures/
errors/skips, and the full Xcode app. Compilation took 832 seconds, simulator tests
81 seconds and the Xcode step 1,425 seconds. Its local root/Android and 1,854 desktop
report cases plus 82 identity/application Chromium cases passed separately.

All 22 provider source/test baselines were compared before the Google increment
was applied over the accepted password/configuration foundation. Differences stay
limited to application-neutral comments/fixture identifiers. S256 PKCE, callback
and nonce/audience correlation, code exchange, browser disposal, pending cancellation
and late-result regressions are included. The iOS host supplies the owned source;
Android keeps its existing nonce-validated Credential Manager path. The direct
OIDC crypto catalog entry reuses version 0.18.4 and existing verified artifacts.
The starter demo login and application-session policy remain unchanged. Local
root checks, Android assembly, 320 desktop and 46 identity/application Chromium
cases passed in 4m20s, with ten runner/six distribution checks and no failures/
errors/skips. Full hosted [CI 40864341588](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40864341588)
passed all three jobs at port `e0c6071`. Apple retained 77 native cases in 22
downloaded suites with no failures/errors/skips, and the full Xcode app passed.
Compilation took 526s, tests 87s and Xcode 589s. This closes the generic port;
actual iOS/APNs/device acceptance remains separate.

## Apple Firebase password configuration

Mobile `196c1171` passed
[Apple run 40855843460](https://github.com/fajarxfce/hris-mobile/actions/runs/40855843460)
before this port: 82 simulator cases in 22 downloaded JUnit suites, zero failures,
errors or skips, and the full unsigned Xcode app. Its initial `64039536` run failed
an existing transport cancellation test; the corrected fixture waits for explicit
engine cleanup under a bounded real-dispatcher deadline. Native compilation took
711 seconds, tests 94 seconds and the Xcode step 1,408 seconds.

Six portable source/test files add bundle-bound public configuration, the Apple
restriction header, invalid/mixed metadata rejection and native plist regressions.
The iOS host supplies the source, identity-data's native tests join Apple CI, and
the iOS test source set uses existing Okio. Source baselines were compared; only
application-neutral comments and fixture IDs deliberately differ. HRIS native
session APIs, account binding, configuration and login policy remain downstream.
Google browser/code exchange is a later Mobile increment, not part of this port.

Starter root checks, Android dev assembly and identity Chromium passed in 1m10s
(1,503 tasks). Reports retain 308 desktop and 31 Chromium cases, zero failures/
errors/skips, plus ten runner and six distribution checks. Independent Apple
[run 40861505322](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40861505322)
passed at `8a75de5`: 62 native cases in 18 downloaded suites, no failures/errors/
skips and the complete Xcode app. Compilation took 611s, tests 63s and Xcode 625s.

## Downstream actual FCM acceptance

On 29 October, unchanged Mobile application `678783b3` passed the
[owned Android FCM case](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/native-fcm-acceptance.md):
two real foreground/background notifications, correct Inbox navigation, unchanged
read/ack state and complete token retirement. Platform retains the opt-in driver,
private host credential and sanitized measurements in `55288bc4`; Mobile records
the result at `f740d658`. No reusable implementation
change arose, so no code/configuration port applies. This downstream proof does
not replace actual iOS/APNs or other target acceptance for Starter.

## Apple CI compilation budget

The generic workflow correction from Mobile `451a2c4d` was ported only after its
[Apple follow-up 40817592306](https://github.com/fajarxfce/hris-mobile/actions/runs/40817592306)
passed 27 simulator tests, the full unsigned Xcode build and report upload. The
original Mobile run had reached the combined compilation/test deadline; separating
compile (25 minutes), tests (15) and Xcode (30), within a 75-minute job bound,
retains all three existing simulator test targets without shortening coverage.

Starter keeps its FluentStarter project/scheme and its three default jobs; Web
remains in verify. Manual `scope=apple` runs only Apple with an independent
concurrency group. Test reports upload even after a failure. Parsed workflow
comparison matches the accepted Mobile Apple job except project/scheme naming.

Local headless root checks passed in 21s (891 tasks: 48 executed, 843 up to date),
with 306 desktop cases in 108 reports, ten Python runner and six distribution
regressions. The application is unchanged since `9a13791`, whose full CI passed.
The changed Apple workflow passed as
[40820482699](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40820482699)
at port `1817e45`. Compilation took 6m28s, simulator execution 47s and the complete
unsigned Xcode app step 8m31s. Downloaded JUnit confirms 27 tests in ten suites,
with zero failures/errors/skips; report upload and cleanup passed. The manual Apple
scope intentionally skipped verify/Windows; their unchanged application sources
retain full run `40648662419`. [Tracked measurements](measurements/ci-2026-10-29.json)
preserve the exact commit, source Mobile proof and both local/hosted results.
No release, signing or physical provider/device acceptance is implied.

## Firebase provider ownership

Twenty reusable provider source/test files derive from Mobile `d191c61d`, with
application-neutral documentation and fixture identifiers. Android Credential
Manager, fixed Firebase REST transport, opaque provider contracts, lazy acquisition,
bounded errors, nonce binding and cancellation/disposal regressions accompany the
port. Host wiring preserves Activity/container ownership. Two additional real
container regressions verify disabled/lazy transport, provider resolution and no
application-session publication from provider proof alone.

Mobile passed 1,837 desktop/UI tests, 119 related Chromium tests and Android
assembly before the port. Its real Firebase email/password acceptance completed
Android login through HRIS MFA, then removed the temporary account. Google account
UI launch/dismissal passed, without Google token acceptance. HRIS exchange and login
policy remain in Mobile. See [the provider contract](firebase-auth.md).

The starter passed root checks with 306 desktop/UI tests, 29 identity-data
Chromium tests and configured Android dev assembly on 28 October 2026. Output is
in `.work/firebase-provider-validation.log`. All 34 newly verified artifact entries
match the already validated Mobile metadata and preserve every prior hash. This
port adds 13 portable provider regressions and two application-container cases.
Remote [run 40648662419](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40648662419)
for `9a13791` passed Linux/Android/Web, Windows and Apple simulator tests/full Xcode
simulator build. Real Google token exchange and iOS Firebase provider acceptance
remain separate from build/container compatibility.

## Notification resource paths

The four notification source/test files from Mobile `b77d991f` preserve validated
hierarchical internal paths on Android. Source baselines matched before copying.
Mobile passed root checks, eight notification-data Chromium tests and Android
assembly before the port; the starter passed the same checks with 291 desktop
tests. HRIS hint queues, selected-company admission and canonical Inbox acquisition
stay in Mobile. See [notification contracts](notifications.md).

## Provider token retirement

Thirteen reusable source/test files match HRIS Mobile `e56ef32b` after checking
their source baselines. Android/iOS callbacks now request the current SDK token
instead of replaying retained token strings. Android, iOS and Web adapters expose
deletion; failed or cancelled deletion does not publish success. The Swift bridge
and simulator regressions accompany the port.

Mobile passed root checks with 1,735 desktop tests; the starter passed 289.
Both passed all six notification-data Chromium tests and Android dev assembly.
Apple execution is tracked separately through CI. Session-bound registration,
backend disable and durable retirement recovery remain application policy.

## Published partition pages and complete command observation

The database contracts and portable regressions match HRIS Mobile `0428aa46`.
`observePublishedCheckpoints` reads bounded metadata pages under a partition
prefix, retaining the visible generation during replacement. `CommandStore.observeAll`
includes every retained command in a partition under the existing account capacity.
Account ownership, cancellation and replacement-login behavior stay in the raw
storage contracts. HRIS draft editing, business outcomes and the pending-actions
page remain in Mobile.

On 28 October 2026, root checks passed with 284 desktop tests. All 25 database
Chromium tests and Android dev assembly passed. No device installation or Apple
build was performed for this port. See [replication storage](replica-storage.md)
and [command storage](command-storage.md).

## Published replica metadata

The metadata-only published-checkpoint observer matches
[HRIS Mobile ff01f11e](https://github.com/fajarxfce/hris-mobile/commit/ff01f11ef04ba7e81c87ec3a2e061644139a424c).
Room and IndexedDB retain visible metadata while a replacement stages, then
publish the new checkpoint atomically. Account/company isolation, replacement
login, removal and observer cancellation share the same portable regression.
Feature decryption, read admission and synchronization status remain application
responsibilities.

Root checks, all 25 database Chromium tests and Android dev assembly passed in
both repositories. See [replication storage](replica-storage.md) for the contract.

## Filter labels at large text sizes

The optional `AppFilterChip.maxLines` and its regression match
[HRIS Mobile 7e79d35f](https://github.com/fajarxfce/hris-mobile/commit/7e79d35f).
The default remains one line. A complete month and year can wrap between
48 dp navigation actions at 320 dp width and 200% text without losing click
semantics. Month selection and attendance policy remain in HRIS Mobile.

Root checks passed with 284 desktop tests, including the new label regression.
Android dev assembly passed. HRIS also verified the control on Android at 200%
text after an offline upgrade without changing retained attendance records.

## Bounded protocol response bodies

The public `readApiBody` boundary matches
[HRIS Mobile 7741c4e](https://github.com/fajarxfce/hris-mobile/commit/7741c4e).
Feature-owned protocols can select a bounded success-body budget while HTTP
problems retain their independent 16 KiB limit. Callers own and clear successful
buffers. Regressions cover one-byte probes, oversized bodies, invalid budgets
and retained structured rejection codes. Expense authorization, range negotiation
and file export policy remain in the application.

Root checks passed with 278 desktop tests, 36 network Chromium tests and Android
dev assembly. HRIS Mobile passed its corresponding checks, network Chromium
suite and Android build.

## Exact decimal formatting

`DecimalAmount` and `formatDecimal` match
[HRIS Mobile d51b291](https://github.com/fajarxfce/hris-mobile/commit/d51b291).
The shared value retains bounded decimal digits and value equality across equivalent
scales. English/Indonesian formatting avoids floating-point conversion. Portable
regressions cover precision beyond integer-safe floating-point values, signed zero,
refunds and invalid input. Payroll contracts and policy stay in HRIS Mobile.

## Enum route arguments

The shared `enumNavType` adapter matches
[HRIS Mobile b58caf8](https://github.com/fajarxfce/hris-mobile/commit/b58caf8).
It provides named enum arguments on every Navigation target and preserves argument
equality when a graph is rebuilt. Unknown restored values fail decoding without
choosing another action. Three regressions cover saved-state round trips, invalid
values and graph identity. Root checks passed with 271 desktop tests, five
shared-presentation Chromium tests, Android dev assembly and application Web
compilation. CI includes the shared browser suite. HRIS routes and overtime policy
remain in the application.

## Controlled time input

The shared `AppTimeInput` matches
[HRIS Mobile a66c787](https://github.com/fajarxfce/hris-mobile/commit/a66c787).
It renders independently controlled hour/minute fields with numeric keyboard
options and caller-provided labels. Consuming forms own range validation, timezone
conversion and confirmation. The regression checks narrow layout at 200% text,
independent edits and disabled input. Root checks passed with 268 desktop tests,
Android dev assembly and application Web compilation. HRIS overtime policy and
durable submission remain in the application.

## Page actions and progress

Fixed page action slots and labelled inline progress match
[HRIS Mobile c99de56](https://github.com/fajarxfce/hris-mobile/commit/c99de56).
The shared regressions keep the last list row above the fixed action at 200%
text size and preserve a busy icon's target while duplicate input is disabled.
Root checks passed with 263 desktop tests, two app Chromium tests and Android dev
assembly. Company selection and its application policy remain in HRIS Mobile.

## Navigation restoration

The scoped navigation saver and deferred graph attachment address the process
restoration crash reproduced in HRIS Mobile. Matching account/company stacks
restore after bootstrap; changed scopes discard saved entries and clear retained
navigation ViewModels. Four new regressions cover process recreation, changed
company, revoked session and repeated recreation during bootstrap. Root checks
passed with 261 desktop tests, two app Chromium tests and Android dev assembly.
Application admission remains with its own use cases.

## Owned file output

The raw save-dialog adapters, bounded sequential writers and failure mapping match
[HRIS Mobile 89ffc4d](https://github.com/fajarxfce/hris-mobile/commit/89ffc4d).
Platform entry points own and close each source. Cancellation and late results
release native handles without deleting a selected path. Repositories and use
cases supply their own mapping, admission and transfer policy; the starter demo
queue does not automatically export files.

## Binary transport

The bounded range reader and shared API response boundary match
[HRIS Mobile c1ef559](https://github.com/fajarxfce/hris-mobile/commit/c1ef559).
Callers select the conditional request header required by their API. The common
reader verifies the returned validator, byte extent and size before exposing
content, while preserving structured HTTP failures and cancellation. Both
repositories passed root checks, network and transfer Chromium tests, and Android
dev assembly. HRIS document admission and storage policy remain application work.

## Scheduler registration

Deferred platform scheduler resolution matches
[HRIS Mobile 3cdbd52](https://github.com/fajarxfce/hris-mobile/commit/3cdbd52).
A task can depend on scheduling when it needs another bounded pass. Its
constructor must finish before foreground registration inspects the task keys.
The shared generated-DI regression covers both task-first and repository-first
resolution, repeated requests and one platform registration. Scheduler disposal
remains owned by the application container.

## File input

The bounded document picker/read adapters and failure boundary match
[HRIS Mobile 6dded5a](https://github.com/fajarxfce/hris-mobile/commit/6dded5a).
Shared regressions cover picker ownership, late results, disposal, native read
cancellation, hashing and browser cleanup. Account admission, encrypted document
mapping and the HRIS upload protocol remain application responsibilities.

## Retained tab state

HRIS Mobile exposed unconditional acquisition on returning to already loaded
tabs. The starter's dashboard already retains its navigation-owned state; its
regression now checks request counts, reactive cache updates and observation
cleanup. The shared architecture guidance distinguishes rendering visibility
from navigation ownership. Attendance and employee-directory policy remain in
HRIS Mobile.

## Android runner recovery

The optional installation deadline and timeout regressions match
[HRIS Mobile d66078bc](https://github.com/fajarxfce/hris-mobile/commit/d66078bc).
An observed VPN transfer exceeded the default three-minute deadline. Callers can
select 30–900 seconds without enabling retries or application launch. Timeout
reports an unconfirmed result; invalid values are rejected before device access.
Both repositories passed all ten runner tests. HRIS endpoints and phone selection
remain application configuration.


Bounded ADB commands, optional per-device port forwarding and runner checks match
[HRIS Mobile 2e12213](https://github.com/fajarxfce/hris-mobile/commit/2e12213).
Both repositories passed their root checks and all seven runner tests. HRIS's
private VPN endpoint, firewall and Android HTTP allowlist remain in that application;
the starter retains its existing HTTPS configuration and in-process demo.

[HRIS Mobile](https://github.com/fajarxfce/hris-mobile) is an application generated
from this template. It exercises the foundation against a real backend and keeps
its own application history.

Reusable corrections to networking, MVI, storage, synchronization, design-system
components, resource ownership, and build conventions belong in this starter as
well. Reproduce the issue with a focused regression test, remove HRIS-specific
assumptions, and validate the change in both repositories. Link the corresponding
commits in their descriptions or delivery records. Do not copy credentials,
employee data, endpoint configuration, or business workflows into the template.

Template generation copies files once. It does not subscribe applications to
updates. Port subsequent changes through reviewed atomic commits; use a clean
cherry-pick only when the application code still matches. Never overwrite a
downstream tree or merge unrelated template histories to synchronize it.

Keep the template's default branch on the latest verified foundation. Preserve
platform-specific checks and report separately which behavior was verified by
unit tests, an actual backend, or a device.

## Reviewed foundation updates

Durable file storage matches
[HRIS Mobile 0267e71](https://github.com/fajarxfce/hris-mobile/commit/0267e71).
The raw SQLite/IndexedDB boundary stores bounded opaque blocks, immutable completion
proofs and versioned checkpoints under a stable account binding. It preserves files
across admitted logins and reserves capacity before import. Encryption, file
selection and application transfer protocols remain consuming data/use-case work.
Both repositories share the storage contract and regression coverage.

The Android convention enables core library desugaring for the API 24 minimum,
matching [HRIS Mobile 76c6375](https://github.com/fajarxfce/hris-mobile/commit/76c6375).
An API 24 emulator reproduced the missing Java time class before that correction
and opened the employee calendar afterward. Application-specific calendar behavior
remains in HRIS Mobile.

The date-only formatter and timezone regression match
[HRIS Mobile 30b51fc](https://github.com/fajarxfce/hris-mobile/commit/30b51fc).
They keep a calendar date independent of the device timezone while applying the
selected language. Both repositories passed root checks and Android/Web builds;
HRIS Android emulator captures cover English and Indonesian dates. Apple builds
remain part of platform CI.

Reusable screen headers, action tiles and page-spacing controls now support the
employee layouts in HRIS Mobile. Their shared regression checks 200% text,
heading semantics, one action per tile and keyboard activation. The starter
receives the components and regression; company switching, inbox and calendar
screen layouts remain in HRIS Mobile.

Foreground location matches
[HRIS Mobile 21eead1](https://github.com/fajarxfce/hris-mobile/commit/21eead1),
including Apple callback ownership at
[b90f908](https://github.com/fajarxfce/hris-mobile/commit/b90f908).
The shared foundation contains raw Android/iOS adapters, a domain repository,
DTO/failure mapping and cancellation tests. Attendance permission policy, company
access, capture evidence, encrypted commands and review remain in HRIS Mobile.

Optional `AppCard` content spacing matches
[HRIS Mobile a871170](https://github.com/fajarxfce/hris-mobile/commit/a871170).
Its zero-spacing default preserves existing callers. Both repositories passed
root checks and Android/Web builds; attendance card layouts stay in HRIS Mobile.

Fixed page bars, adaptive menu grids, text actions and preference rows match
[HRIS Mobile bb1cc5c](https://github.com/fajarxfce/hris-mobile/commit/bb1cc5c).
Shared UI tests cover scroll-independent back/refresh actions, 200% text, keyboard
activation and radio/switch semantics. Both repositories passed root checks and
Android/Web builds. Home attendance, company switching and employee service
admission remain in HRIS Mobile.

Settings uses the same fixed back action and language/security preference rows as
[HRIS Mobile 99b3cce](https://github.com/fajarxfce/hris-mobile/commit/99b3cce).
The starter retains its account roles, permission list and access refresh action.
Its assembled UI test covers language changes, persistence and back navigation.

Root-tab Back handling and single-line navigation labels also support the
[HRIS Mobile navigation update](https://github.com/fajarxfce/hris-mobile/blob/main/docs/template-updates.md#root-navigation).
Shared regressions cover inner-page return, cancelled gestures, rapid Back during
the root transition, platform fallback and full accessible labels at 200% text.
Employee directory policy, Inbox integration and service admission remain in
HRIS Mobile.
Both repositories passed root checks and Android/Web builds. HRIS Android
emulator checks also reproduced and verified the rapid-Back correction.

## Month selection

The controlled `AppMonthPicker`, optional filter-chip icons, and `formatMonth`
match [HRIS Mobile f531200](https://github.com/fajarxfce/hris-mobile/commit/f531200).
The picker owns layout and selection semantics; callers supply the available
months, years, labels, and actions. Tests cover disabled choices, 200% text,
English/Indonesian names, and timezone-independent formatting. Root checks,
the localization Chromium suite, Android dev, and production Web builds passed
in both repositories. Attendance rules and screen state stay in HRIS Mobile.

## Storage observer recovery

Dashboard subscription recovery matches
[HRIS Mobile eee789e](https://github.com/fajarxfce/hris-mobile/commit/eee789e).
A storage source can report a failure and complete while its outer session
observer remains active. Successful refresh, pagination and saved-item writes
reopen the owned content subscription; a failed operation does not trigger an
automatic retry loop. Cancellation checks reject obsolete results before state
changes or subscription replacement. The starter regression reproduces the
completed-source failure and verifies cancellation, recovery and disposal.

## Multiline text fields

Optional multiline input matches
[HRIS Mobile 844314c](https://github.com/fajarxfce/hris-mobile/commit/844314c).
`AppTextField` retains its single-line default; a caller can supply `maxLines`
without replacing the shared field or owning keyboard internals. Both repositories
passed root checks and Android/Web builds. The shared regression checks line layout
and newline-preserving editing at 200% text. Leave reasons, validation and command
orchestration remain in HRIS Mobile.

## Content categories

The controlled `AppTabs` component and its regressions match
[HRIS Mobile c992f95](https://github.com/fajarxfce/hris-mobile/commit/c992f95).
Selection stays with the caller; the component supplies tab semantics, wrapping
labels and touch targets. Root checks passed with 265 desktop tests, two
application Chromium tests and Android dev assembly. HRIS document navigation
and acquisition policy remain in the mobile application.


## Stable read ownership

The raw replication contract now accepts `ReplicaScope`: a current session guard
and an optional stable API/account key. Composite database keys keep accounts and
company/collection partitions independent. New-session reads can use retained
account data, while the previous session cannot publish, remove or recreate it.
A missing account key explicitly selects temporary session data. Existence checks
include inactive accounts so encryption adapters do not replace a lost key over
retained ciphertext.

The shared Room/IndexedDB schema upgrades and regression contract match HRIS
Mobile. Both repositories passed root checks, database Chromium tests and Android
dev assembly. HRIS separately owns encrypted Inbox formats, company admission,
credential-bound cursor replacement and offline UI acceptance.

## Indexed replica queries

The shared replica store adds optional equality tags and atomic multi-page reads,
with compatible Room/IndexedDB migrations. `contentIndexes` derives scoped opaque
filter tokens using HKDF-SHA256 and HMAC-SHA256. Storage performs no feature
selection, permission policy or synchronization. The implementation and portable
regressions match HRIS Mobile; Leave integration remains application work.

Root checks, 41 database/secure-storage Chromium tests and Android dev assembly
passed. No device performance or business sync coverage is inferred from these checks.
