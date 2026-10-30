# Agent continuation — Compose Fluent Starter

Updated 30 October 2026 (UTC). This repository is the reusable foundation. HRIS
Mobile was generated from it and is the proving ground: validate generic fixes
there before porting their implementation and portable regressions here. HRIS
business policy, backend adapters, routes and deployment configuration stay in
the consuming application.

## Completed workspace audit — 30 October 2026

The complete HRIS Platform/Mobile/applicable Starter audit is closed. Platform
`64eb0d14` records all 61 initial requirements and eleven detailed module reviews
accepted. Final [CI 41094285531](https://github.com/fajarxfce/hris-platform/actions/runs/41094285531)
passed all ten jobs at `93438f99`, with 3,090 API cases, 104 active worker cases,
1,111 mocked browser scenarios, 56 actual workflows and schema-103 recovery.
Temporarily stale GitHub step metadata was reconciled against final logs, reports
and artifact digests; no cancellation, rerun or source change was necessary.

The private API/worker now serve verified application `2c2a59e3` after a fresh
schema-103 recovery checkpoint. Runtime health, migration/identity/session/document
integrity and unchanged environments are verified; SMTP remains disabled. Dashboard
`1e0522c0` and the installed Mobile application `3c6db4eb` remain unchanged.
[Platform handoff](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/agent-handoff.md)
and [runtime evidence](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/measurements/final-development-rollout-2026-10-30.json)
retain recovery locations, old image tags and current ownership. Mobile closure is
committed at `5ffb7300`; its fifteen-area review retains independent full CI
`d170810f`/`41077194465` and the earlier actual Android Google/Home/FCM acceptance.
Physical iPhone testing is explicitly waived; simulator and Xcode checks passed.

[Applicable foundation review](foundation-review-2026-10-30.md) and
[its source/CI evidence](measurements/foundation-review-2026-10-30.json) remain
accepted at `7605916`. All 657 identical shared files and 60 differences are
classified, and shared dependency digests match. Current application/build/test
sources retain accepted `77063862`/`40916187442`, with the separately proved two
artifact-upload workflow changes. No additional generic implementation port is due.
This closing update changes documentation only; no implementation, CI or local
test remains active. For new work, prove reusable fixes in Mobile before porting
here and keep HRIS policy/configuration in its application. Historical pending
paragraphs below are not an active queue. Undefined future extensions and production
release are not represented as completed features or deployments by this audit.

## Retained acceptance history

Earlier pending statements below are historical; use the active queue above.

Current downstream acceptance: Mobile application `3c6db4eb` plus test/docs source
`d170810f` passed all four jobs in [CI 41077194465](https://github.com/fajarxfce/hris-mobile/actions/runs/41077194465).
Downloaded reports verify 1,994 desktop, 1,676 browser, ten build-logic and 104 Apple
simulator tests; the full Xcode app and other target builds pass. The normal private
API APK is installed on the authorized phone with its binary hash verified and no
UI launch. [Mobile final evidence](https://github.com/fajarxfce/hris-mobile/blob/feat/native-push/docs/measurements/document-metadata-final-ci-2026-10-30.json)
retains every job/step, artifact reports, failed predecessor and install-only result.
Platform runtime is schema 103/dashboard `1e0522c0`; SMTP remains disabled. Older
schema-101/phone-72341ef6 statements below are historical. Both metadata PRD rows
are now accepted; the full audit remains active.

The HRIS metadata browser capacity test now has the same bounded 20-second I/O
deadline already used by Starter's generic database capacity test. HRIS metadata
models, API adapters and recovery flows do not belong in the template. No new
implementation port is due. Platform Administration `3c8fb15a` is pushed with full
CI `41081153648` active; Reporting's seven reproduced late-policy MFA cases pass
focused checks and broader validation continues. Finish remaining Platform,
Mobile and applicable Starter requirements without treating these increments as
the complete objective.


The full PRD review remains active. Platform's [audit](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/feature-audit-2026-10-30.md)
now retains forty-one reviewed rows across Approvals, Communications, Identity, Expenses,
Leave, Documents, People and Administration; twenty Platform rows and full Mobile review remain. Keep the original
scope; accepted increments do not establish whole-product completion.

- Platform `8014d733` adds optional announcement email, atomic publication capture,
  bounded SMTP delivery and private observations. Local acceptance, schema-102
  recovery and all ten [full-CI jobs](https://github.com/fajarxfce/hris-platform/actions/runs/41005223374) passed.
- `bbb99b48` exposes immutable shared approval history. `fcfdaf26` corrects
  read-completion MFA/delegation expiry; 60 approval API and 13 domain cases,
  quality/packages and two actual API/browser workflows passed.
- `737d39a7` corrects thirteen Communications read-expiry reproductions. All
  106 selected API, 13 domain and three repository cases passed, plus the actual
  API/worker/browser/SMTP workflow (37.1s; 1.0m with setup).
- `a45d5a9b` records Identity's five initial rows and eight detailed sections.
  Its source trees match accepted `8014d733`; downloaded reports verify 190 API,
  35 domain, 22 data, 17 worker and 95 Chromium cases, including eight real workflows.

- Platform `7a1c0251` and `8e5dd3db` complete Document read/command assurance.
  Twenty read reproductions and nineteen command/enrollment reproductions now
  pass; one inventory-replay scenario was an already-correct positive control.
  All 243 selected API, 18 domain and nine data cases, quality/packages and four
  actual API/browser workflows passed (42.5s). Four Document rows retain acceptance. The explicit category requirement
  in `documents.02` is reopened after the People audit found missing metadata.

- Platform `5b60aba3` completes the Expense assurance/input correction and its
  six-row/nine-section review. Twenty-nine genuine failed cases now pass, plus
  ordinary-scope controls and delegator-account wait regressions. Broad/final runs
  cover 335 distinct API, 20 Expense domain and 115 Reporting cases; quality/packages
  and four actual browser workflows pass (1.2m with setup). Scope includes current
  enrollment, receipt and decision delegation, and stable admitted command inputs.

- Platform `c68b721a` and `70b92e59` complete Leave read/command/delegation
  assurance and its five-row/eleven-section review. Ten reader and twenty-five
  command/scope failures now pass. Broad and final targeted runs cover 485 distinct
  API/OpenAPI cases (471 broad plus fourteen added cases), with 65 domain cases,
  quality/packages, six actual browser workflows (50.4s) and three actual worker
  workflows (53.4s). Scope includes final MFA, original receipt approval, beneficiary
  exclusion, stable policy input and current delegated access. Eight added capacity
  compatibility cases preserve ordinary/direct reads without permitting oversized
  delegated decisions; fixture setup failures are explicitly excluded from defects.

[Approval CI](https://github.com/fajarxfce/hris-platform/actions/runs/41009137720)
passed all ten jobs for `fcfdaf26`. Communications/Documents source `8e5dd3db`
passed all ten [CI 41013958569](https://github.com/fajarxfce/hris-platform/actions/runs/41013958569) jobs.
Expense [CI 41018107365](https://github.com/fajarxfce/hris-platform/actions/runs/41018107365)
passed nine jobs and all fourteen worker scenarios, but exceeded the five-minute
step budget (5.2m); later storage/export checks were skipped. Platform `84d37ad6`
records the failure and sets bounded eight-minute worker/twenty-minute job budgets.
Final Leave source `70b92e59e7b41bdafe9f9d1c4b9d5106412777f5` passed all ten jobs in
[CI 41023645590](https://github.com/fajarxfce/hris-platform/actions/runs/41023645590),
including unchanged Expenses, worker, private exports and recovery. Platform
`7dae1e26` records the exact outcomes and the failed predecessor.

People reader correction `b0b7c21c` passed 427 API/OpenAPI, 50 domain and fourteen
repository cases, quality/packages (1124s), three actual browser workflows (36.5s)
and two actual worker/browser workflows (37.3s), including setup. The later command
and Organization correction has fifty reproduced failures plus four prior controls;
all 54 focused API, 52 domain and fourteen data cases plus quality/packages passed
(142s). Final broad acceptance passed 497 API cases. Two legacy worker-import
fixture failures were corrected; all 104 ordinary worker cases then passed with
one independently accepted manual test skipped (226s). Production/package hashes
matched the broad API run. Three normal browser workflows passed in 36.1s and two
worker/browser workflows in 36.8s, including setup. Platform `158b81ac` commits the
complete correction and evidence. Exact source `158b81acce89f637dbf715bb573fffb7b99b92c9`
passed all ten jobs in [CI 41032184925](https://github.com/fajarxfce/hris-platform/actions/runs/41032184925).
Its complete terminal evidence is retained in the Platform repository.

The audit confirmed missing distinct document category and foreign identity/document
business-validity metadata in `documents.02` and `people.03`. Upload deadlines are
operational; tax residency already belongs separately to Payroll. The previous
Documents review omitted category, so that row is reopened. See the
[gap and continuation](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/document-identity-metadata-gap-2026-10-30.md).
Platform `27e7f77e` implements category/identity/business validity, immutable history,
initial upload and dashboard corrections. Combined accepted local selections cover
689 API, 104 ordinary worker, 27 database, 18 domain and nine data cases, 4,054
dashboard unit cases, 25 mocked and five actual browser workflows. Full
[CI 41038369597](https://github.com/fajarxfce/hris-platform/actions/runs/41038369597)
finished with nine jobs passed and one recovery failure for exact `27e7f77e722da9e8ac2bde47836353ef92dcc3b3`.
The verifier still limited schema to 102. Platform correction `292a30f1` now passes
42 Python tests and actual schema-103 restore in 57.356s, including three metadata
snapshots, company RLS, immutability and deferred history/head checks.
[Replacement CI 41043798660](https://github.com/fajarxfce/hris-platform/actions/runs/41043798660) passed all ten jobs for exact `292a30f14498b2eee97a9255f22fe645639658e5`, including
normal/worker browser integration, private exports and schema-103 recovery. Platform Git
retains every terminal job/step and the downloaded recovery summary: 104 objects,
2,098,795 bytes, restored metadata/history/RLS/constraints and completed cleanup.
The original run remains recorded as a failure; no CI run is pending for this source.

Mobile `7f352cfd` retains the new facts in its owned encrypted document projection.
A genuine metadata-loss reproduction is fixed. Root `:check` passes all 1,869
desktop cases; Document Chromium suites pass 123 cases and the dev APK builds.
Its [contract and measurements](https://github.com/fajarxfce/hris-mobile/blob/feat/native-push/docs/document-business-metadata.md)
record old-format/unchanged-version refresh, server capability transitions,
staging/restart retention and persistent admission denial after failed cleanup.
The new direct calendar-date dependency uses the existing locked version. Shared
finance/leave evidence remains free of identity fields. These HRIS-specific model,
API and schema changes require no Starter implementation port.

Mobile initial-upload increment `41fe79f9` is pushed. Category/identity/printed dates
freeze with the encrypted original intent and retain old wire omissions. All 1,878
desktop and 131 Document Chromium tests pass, with root quality and the dev APK
(163.698s). The assembled form reproduced and fixed validation feedback hidden above
a long form; its route now reveals the retained failure. HRIS business behavior
continues to require no Starter implementation port.

Mobile `e2690fd6` completes first definite upload-rejection recovery. Explicit
reconfirmation replaces only an exact observed rejected intent; bytes remain intact,
and earlier uncertainty stays pinned. Root checks, the dev APK and Document browser
suites pass (189.354s): 1,888 desktop and 140 browser cases. Two genuine baseline
failures and existing safety controls are retained in Mobile Git, along with actual
encrypted competing-write/restart and assembled-app acceptance. This remains HRIS
business policy, with no generic Starter implementation port.

Mobile `3ce42fed` adds validated header/history/correction transport; `966e883c`
adds encrypted metadata/history and per-resource read admission. Both are pushed.
Current root checks, dev APK and Document browser suites pass in 184.621s: 1,912
desktop and 164 browser cases. Actual SQLite/IndexedDB tests retain 1,000 immutable
revisions and verify exact-page reads, restart, scope, lost keys, cancellation,
competing writes and pending/failed admission storage. These changes reuse existing
storage contracts and require no Starter implementation port.

Mobile `f282b027` adds admitted reads and the assembled detail/history route. Thirteen
domain and two UI cases cover ownership, finite paging, legacy support, cancellation,
offline history and denial recovery. Two reproduced publication failures now retain
an earlier denial when company policy expires during local storage. Root quality,
dev APK and Document browser suites pass in 197.437s: 1,927 desktop and 177 browser
cases, all without failures/errors/skips. Mobile Git retains exact cases, source/APK
hashes and earlier compilation/method-length corrections. This commit is pushed;
mutable working copies and the versioned correction editor remain in progress.

Mobile `b0f93d67` retains encrypted correction commands using the existing shared
CommandStore/key and exact record/revision checks. Three domain and six actual
SQLite/IndexedDB cases pass; full local checks covered 1,936 desktop and 186 browser
cases in 193.331s. `82519a85` adds explicit intake, admitted local observation, bounded
manual delivery and review. Fourteen new domain scenarios cover original retries,
current ownership/MFA, uncertain outcomes, later headers, acknowledgement and resource
subscriptions. Full root checks, dev APK and Document Chromium suites pass in 180.515s:
1,950 desktop and 200 browser cases, without failures/errors/skips. The first twelve
flow failures were a read-only fixture; it was corrected with a separate denied control.
Both commits are pushed. Mobile Git retains exact cases, sources and APK hashes;
no automatic delivery or current-header replacement is introduced.

Mobile `fbd6a717` and `36ddd5ed` complete encrypted mutable working copies and
admitted open/save/discard/observation, including matching-draft cleanup after
confirmed acknowledgement. Exact local acknowledgement recovery preserves concurrent
edits and the original reviewed header. Full checks passed 1,968 desktop/218 browser
cases and dev APK in 182.812s. `8ca77f19` then fixes a reproduced catalog exposure:
Back from denied details previously showed cached title/file. Per-resource gating
now redacts that record, preserves paging/other rows and permits neutral admitted
recovery. Full checks passed 1,973 desktop/221 browser cases and dev APK in 183.001s;
two screenshots were inspected. All commits are pushed with durable Mobile evidence.
These are HRIS business changes; no generic Starter implementation port is due.

Mobile `832bbc09` and `017aa506` now complete the locally validated metadata editor
and independent retained overview. The editor preserves incomplete input and the
reviewed header, coalesces local writes, freezes confirmed input and reuses original
operations after response loss. Acquired current facts remain separate from old
receipts. Two real command-admission failures and hidden stale-draft feedback were
corrected; its full checks passed 1,980 desktop/223 browser cases plus the dev APK.

The overview reopens local drafts/corrections after published binary rows disappear,
with bounded 20-copy pages, original cursors and neutral resource recovery. Opening
or reloading sends no HTTP or commands. Two reproduced presentation failures showed
that one failed reader could leave another stale private projection visible; both
readers must now be ready/admitted. Full root checks, dev APK and Document browsers
passed in 190.845s: 1,987 desktop/224 browser, zero failures/errors/skips. The actual
storage/controlled-HTTP UI cases pass offline draft recovery and original retry after
later administrative edits; three overview screenshots were inspected. Both commits
are pushed; Mobile Git retains exact cases, source/APK hashes and failed predecessors.
No runtime/phone/provider change or reusable Starter implementation port occurred.

Mobile `3c6db4eb` completes Account's bounded metadata summary/direct overview link:
1,994 desktop/230 browser cases and dev APK pass locally. Platform `1e0522c0` records
actual Android/API/private-storage acceptance in 432.451s: incomplete draft and
original command survive two offline process restarts; retry after a later admin
edit confirms the same operation/body without replacing newer facts. Done clears
the command/matching draft, all seven pending groups empty, and three history
revisions plus binary facts remain intact. Summary reloads stay 36→36 and 48→48;
all owned fixture resources were cleaned. User runtime and phone remain unchanged.

Mobile `d170810f` records this proof and fixes a CI browser capacity deadline:
Mocha's two-second default failed its real 1,000-revision storage test. A bounded
twenty-second module configuration passes all 75 affected browser cases locally
(21.607s), preserving every assertion. This HRIS-only module does not exist in
Starter; Starter's generic database capacity tests already use that same deadline.
No generic implementation port is due.

Platform `b241d5e8` accepts four People rows against unchanged implementation and
completed matching CI. Mobile first metadata CI `41071828262` passed 104 Apple
tests and the complete simulator app, while retaining the Linux browser deadline
failure. Replacement full CI `41077194465` tests exact `d170810f` and remains active.
The current private schema-101 stack has a fresh verified checkpoint and staged
replacement images/dashboard; runtime migration and phone installation are pending.

Finish complete hosted Mobile verification and the controlled schema-103
API/static-dashboard/Mobile rollout, followed by detailed People review and
remaining Platform/Mobile/Starter scope. Neither metadata requirement nor the
full PRD goal is accepted yet. Reusable foundation fixes discovered during this
work must still be proven in Mobile first.

The private API remains schema 101 with SMTP disabled. No new APK installation or
live rollout occurred. Preserve the Google binding, installed phone and independent
static dashboard snapshot. Phone delivery remains install-only with advance notice;
physical iPhone testing is explicitly waived.

## Distribution configuration — accepted

The audit fixes selected release API precedence over per-environment project
properties, optional app-link configuration through signing and packaging, and
31 missing SBOM POM verification entries. The original artifact hashes remain
unchanged. Android checks the normal test configuration before compiling the
selected release; Xcode forwards the selected backend/API explicitly. The 15
portable files retain Starter's application IDs, demo/remote choice and three-job
CI. HRIS's remote-only policy, build-version UI fixtures and explicit dev-network
XML attributes remain in Mobile.

Mobile source `3dc81d00` passed all 4 jobs in full
[CI 40909156570](https://github.com/fajarxfce/hris-mobile/actions/runs/40909156570). Downloaded Apple reports
confirm 104 tests in 28 suites, zero failures/errors/skips;
compilation/tests/Xcode took 791s/122s/1295s. The complete Xcode app, native/domain
artifacts and explicit selected distribution API/backend assertions passed.

Starter port `77063862` passed all 3 jobs in full
[CI 40916187442](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40916187442). Downloaded Apple reports
confirm 84 tests in 24 suites, zero failures/errors/skips;
compilation/tests/Xcode took 724s/81s/744s. The complete Xcode app, native/domain
artifacts and explicit selected distribution API/backend assertions passed.

Actual local signed Android APK/AAB/SBOM exercises passed in 215.572s on Mobile
and 136.647s on Starter. Both verify the selected release API and native manifest
in APK and AAB, actual signatures, retained domain files and checksums. Mobile
passed 1,860 desktop cases; Starter passed 326 desktop cases in 112 suites. Both
passed 17 distribution regressions. Normal dev builds and production SBOMs were
restored afterward (39s Mobile, 23s Starter), with fixture API/host values absent;
Starter's normal backend remains demo. Every disposable signing key was removed.

A workflow-only follow-up at Mobile `d7e445a8` and Starter `4c794e7` retains
`.well-known` files in the manual artifact upload. The pinned action's actual
`@actions/glob` 0.5.0 selection reproduced five files with both domain statements
missing on each signed fixture; the correction selects all seven and every
checksum matches. Both workflows include hidden files only in the packaged
artifact directory. This separate proof does not invoke upload; application/native
code and ordinary CI definitions remain at the full-CI sources above.

No CI, local check or reusable port remains pending for this audit. No distribution
workflow, upload or phone installation was performed; the installed Mobile APK
remains `72341ef6`. The private server and accepted Google binding are preserved.
Real signed Apple IPA, APNs, public domain/DNS/TLS activation, physical GPS and
production operation retain their separate target requirements. Physical iPhone testing was
explicitly waived; simulator and unsigned Xcode proof does not imply device delivery.

See [distribution evidence](measurements/distribution-2026-10-29.json).

## Previous implementation checkpoint — HTTPS links

Final Mobile source `72341ef6` passed all four jobs in full
[40895095518](https://github.com/fajarxfce/hris-mobile/actions/runs/40895095518).
Downloaded Apple reports confirm 104 cases in 28 suites, zero failures/errors/skips
and the complete Xcode app; compilation/tests/Xcode took 701s/77s/1040s.
Starter port `738db36` independently passed all three jobs in full
[40895798634](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40895798634),
with 84 Apple cases in 24 downloaded suites, no failures/errors/skips and Xcode;
compilation/tests/Xcode took 561s/84s/703s. Generated Android/Apple association
checks passed, and the Xcode/Apple artifact success markers were inspected.
No CI or owned local acceptance remains active from this increment.

The implementation queue is complete for this development pass. Physical iPhone
testing was explicitly waived. Public domain/DNS/TLS association, actual APNs,
physical GPS and production deployment remain separate configuration/target
observations; no result for those is inferred from builds or fixtures. Default
native builds do not enable an unconfigured HTTPS host. Reopen implementation for
an observed failure or a documented requirement; do not recreate accepted modules
from old next-step paragraphs.

## HTTPS application-link foundation

Source Mobile `d36a1bc9` passed all four full CI jobs in
[40887772486](https://github.com/fajarxfce/hris-mobile/actions/runs/40887772486)
before this port: 103 native cases in 28 downloaded suites, no failures/errors/
skips, configured native/domain artifacts and the complete Xcode application.
Compilation took 648s, tests 64s and Xcode 1082s. Its local root/Android checks
passed 1859 desktop cases and 26 common/app Chromium cases. The real Android
HTTPS scoped/offline/archive case passed in 3.2m/3.6m with 28 requests and no
company mutation before the generic foundation was applied here.

Nine portable source/check files initially matched the foundation exactly; two
parser files now include the separately proved correction recorded below. The five Apple source/scheme
baselines matched after normalizing the application name. Starter keeps its
bundle IDs, `FLUENT_ENVIRONMENT`, three-job CI matrix and four-route demo policy.
An independent real-container UI regression exercises HTTPS through onboarding,
login, invalid host and background/resume alongside the original custom URL case.
No HRIS session, company/resource rules, web landing page or private config is
copied. Local root/Android/Chromium checks passed in 1m28s after the Unicode correction:
326 desktop cases in 112 suites, seven common/four app Chromium cases and six
build-logic cases, with no failures/errors/skips. Ten runner and six distribution
checks passed. All three configured Android manifests, Apple entitlements and
public domain files passed separately in 13s. The initial port run found missing
JUnit test setup; the existing Mobile catalog entry/test dependency and root-check
wiring were added without changing verified artifact hashes. Its corrected base
run passed in 4m26s before the final parser delta.

Mobile parser correction `72341ef6` reproduced Unicode host case-folding aliasing,
then passed seven JVM/seven Chromium cases before the two files were copied here.
Full Mobile run `40895095518` covers that final source independently of the accepted
foundation run. Starter full [CI 40895798634](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40895798634)
passed all three jobs at `738db36`. Its 84 Apple cases in 24 downloaded suites
have no failures/errors/skips; Xcode and generated associations passed. Compile/
tests/Xcode took 561s/84s/703s. Final Mobile `72341ef6` also passed all four jobs
in `40895095518`, with 104 native cases/Xcode. Both runs are complete and accepted. [Measurements](measurements/app-links-2026-10-29.json)
retain both source boundaries, the initial failure and final local results.

## Current user direction — 29 October 2026

The user explicitly waived physical iPhone testing for this completion pass
("iphone gkpp gk ditest juga") and requested continued implementation through
completion, with each atomic commit pushed. Do not ask for an iPhone again or
keep that test as a completion blocker. Simulator/contract tests and Xcode builds
remain required; do not claim actual iOS sign-in or APNs delivery from them.
Configurable HTTPS admission/native associations and the independently verified
Starter port are complete; retain the exact source/destination proof above.
The controlled domain remains a deployment setting, not a reason to leave the
configurable implementation unfinished. Preserve the accepted Google binding,
phone data and running private development stack.

## Start here

Read `AGENTS.md`, [architecture](architecture.md), [downstream development](downstream-development.md)
and [validation](validation.md). Inspect `git status`, recent commits and upstream
before edits. The current branch is `fix/push-token-lifecycle`. The latest accepted
Apple Google port is `e0c6071`, following password/configuration port `8a75de5`.
Both have passed their independent hosted verification below.

The preceding increment ported iOS Google browser/code ownership from Mobile `43cd8ec6`, after
its full run `40858176601` passed all four jobs, 97 native cases and Xcode. All
22 provider source/test baselines matched before copying; only application-neutral
comments/fixture identifiers differ. Starter root/Android/Chromium validation
passed in 4m20s: 320 desktop cases, 43 identity and three app Chromium cases, plus
ten runner/six distribution checks. Port `e0c6071` passed all three jobs in
[40864341588](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40864341588):
Linux/Android/Web, Windows and Apple. Downloaded Apple reports retain 77 native
cases in 22 suites with no failures/errors/skips, and the complete Xcode app passed.
Compilation took 526s, simulator tests 87s and Xcode 589s. HRIS server exchange,
bindings, login policy and configuration are
excluded. [Downstream development](downstream-development.md) records the source proof.

The preceding password/configuration port `8a75de5` follows Mobile `196c1171` and
its accepted Apple run `40855843460`. Starter root/Android, 308 desktop cases and
31 identity Chromium cases passed in 1m10s. Its independent Apple verification passed in
[run 40861505322](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40861505322),
with 62 native cases in 18 downloaded suites, no failures/errors/skips and the
complete unsigned Xcode app. Compilation took 611s, tests 63s and Xcode 625s.
This password-only run does not cover the later Google port. Matching local iOS
plists for all three existing Starter apps were fetched and remain ignored/mode 600.

Android Google → Firebase → HRIS → company/Home is now accepted on the user-operated
phone, with a matching server session; Platform `5fd21caf` also records a successful
post-login recovery checkpoint. Do not request Home confirmation or the supplied
Firebase credentials again. Real iOS/APNs/device acceptance remains separate.

The local siblings are `/home/fajar/hris/hris-mobile` (`feat/native-push`) and
`/home/fajar/hris/hris-platform` (`feat/firebase-auth`). Their tracked continuation
pages are [Mobile](https://github.com/fajarxfce/hris-mobile/blob/feat/native-push/docs/agent-handoff.md)
and [Platform](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/agent-handoff.md).
The latest downstream [actual FCM acceptance](https://github.com/fajarxfce/hris-platform/blob/feat/firebase-auth/docs/native-fcm-acceptance.md)
passed in Platform `55288bc4`, recorded by Mobile `f740d658`, with the user's
supplied private server credential and unchanged Mobile application `678783b3`: two generic Android foreground/background notifications, correct Inbox
navigation, unchanged unread/unacknowledged items, and complete token retirement.
The fixture passed in 6.2m/6.6m including setup, with 55 requests and no native
company mutation. All owned resources were cleaned. There is no reusable code
change to port; this proves one HRIS Android provider flow, not actual delivery on
every Starter target. Do not request the already supplied FCM credential again.

The initial implementation map is reconciled in Platform's current PRD status;
physical/production observations remain separate. Platform `b7f42070` integrates validated
push-delivery observations (`409abad4`), original payroll comparison (`f757bffd`)
and actual one-hour export retirement (`1abdbce9`). Subsequent native fixtures
`578a5031` (Overtime), `97e33310` (Expense), `ccae6f8f` (Leave) and `a837320f`
(Attendance) pass real API/Android response-loss, offline restart and exact replay.
The six Mobile pending-action observers now each have a native recovery example;
those examples retain their specific scope and used Mobile application `02743a9f`.
Later Mobile `678783b3` corrects English Expense count copy, with root/Android checks. No generic implementation port was
needed for these HRIS fixtures; the then-current Starter application was `9a13791`,
whose cross-platform CI passed. The later Apple provider ports are recorded above.

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
Those earlier runs and Google port run `40864341588` are complete and accepted.
All earlier native fixture resources were cleaned, emulator data retained and
location restored. Actual HRIS Android FCM and Google exchange subsequently passed
as recorded above. Physical/iOS provider acceptance still needs actual external
input; build results do not close it.

The user authorized continued work and pushing every atomic commit. Preserve
unrelated edits, omit co-author trailers and retain implementation/tests/docs
together. A Git push is not release or live-distribution authorization. Tracked
documents are the durable handoff; `.work` and chat history are supplementary.

## Validated ports

| Port | Source and destination | Recorded evidence |
| --- | --- | --- |
| HTTPS registration and ASCII URL admission | Mobile `d36a1bc9` + `72341ef6` → Starter `738db36` | Source foundation/full final CI and actual Android acceptance; independent Starter full CI `40895798634`, 84 native cases/Xcode and local 326 desktop/11 browser cases. |
| Apple Google browser/code ownership | Mobile `43cd8ec6` → Starter `e0c6071` | Full Mobile run `40858176601` passed all four jobs and 97 native cases/Xcode before porting. Full Starter run `40864341588` passed all three jobs and 77 native cases in 22 suites/Xcode, with local root/Android/Chromium proof. |
| Apple Firebase password/configuration | Mobile `196c1171` → Starter `8a75de5` | Mobile run `40855843460` passed 82 native cases/Xcode before porting. Starter run `40861505322` passed 62 native cases in 18 suites and the full Xcode app; root/Android/Chromium checks passed separately. |
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
accepted on Android. The user subsequently confirmed Google → Firebase → HRIS →
company/Home on the installed Android phone, with independent server-session
metadata. Those downstream facts do not prove provider delivery on every target.

Local ignored Android config uses project `cmp-fajar`. The consuming HRIS server
now has its private credential, and actual Android FCM delivery passed. iOS requires
its own Firebase/APNs/device setup. No service account, private key or app-specific
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
