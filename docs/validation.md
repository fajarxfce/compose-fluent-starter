# Validation

## Pending-work storage ports

On 28 October 2026, `xvfb-run -a ./gradlew :check
:core:database:wasmJsBrowserTest :apps:android:assembleDevDebug` passed with JDK 21,
Android SDK 36 and Node 22.23.3. The root check covered 284 desktop tests; Chromium
ran all 25 database tests. Portable regressions now also cover published partition
pagination, staging visibility, observer cancellation and full-capacity retained
command observation across logout and replacement login.

The twelve database source/test files match the validated Mobile change `0428aa46`.
This is an automated storage and assembly check, with no phone/emulator installation
or Apple execution. Local build output is in `.work/pending-storage-validation.log`.

## Owned file output

Complete root checks passed with 257 desktop/JVM tests. File-I/O passed 21
Chromium tests, localization passed two, and app wiring passed two. Android dev
assembly and production Web distribution passed; Web retains three existing
bundler warnings. Shared native/browser regressions cover bounded writes, partial
writes, zero progress, cancellation, late picker results, disposal and read/write
failure mapping. The Apple filesystem tests are part of the existing simulator CI
target and were not run on this Linux host. No native save-dialog device acceptance
was performed for the starter demo.

## Bounded binary responses

Root checks passed with 249 desktop tests. Chromium passed all 34 network and five
transfer tests, and Android dev assembly passed. The shared range reader verifies
strong validators, exact ranges and bounded response sizes, rejects unexpected
full responses, and cancels pending or oversized reads. HTTP failures keep their
status and structured API problem without exposing the raw response body.
No starter APK was installed for this transport update.

## Deferred scheduler acquisition

Root checks passed with 242 desktop tests. The sync data suite passed all three
Chromium tests, and Android dev assembly passed. A generated-DI regression resolves
a task that requests subsequent work, including task-first and repository-first
construction. Both orders defer platform registration until the scheduling call
and reuse it for later requests. This reproduces the dependency cycle exposed by
HRIS Mobile's resumable upload integration.

## Retained tab state

Root checks passed with 230 desktop tests and seven Android runner tests. The
dashboard regression verifies repeated tab changes without new acquisition or
storage subscriptions, reactive cache updates and cleanup when its navigation
owner is cleared. The existing dashboard behavior already follows this contract;
this update adds coverage and lifecycle guidance. HRIS-specific tab acquisition
changes and Android acceptance remain in HRIS Mobile. No starter APK was installed
for this test/documentation update.

## Android runner recovery

Root checks passed with 229 desktop tests and seven Python runner tests. Coverage
verifies explicit-device installation, no launch in install-only mode, optional
forwarding before installation, failure without installation, port bounds and
finite ADB command timeouts. The Gradle build uses `--no-daemon`. This tooling-only
increment did not rebuild or install the starter application.

## Durable file blocks and checkpoints

Root checks passed with 229 desktop tests. All 23 database Chromium tests passed,
including 11 new cases shared with SQLite. Coverage verifies atomic block/progress
writes, immutable completed content, checkpoint contention, stale deletion, account
replacement, bounded reservations/pages, cancellation, independent connections,
restart and migration with retained commands. Android dev assembly and production
Web distribution passed; Web retains its three existing bundler warnings.

The raw store accepts opaque envelopes. Encryption, file selection and application
transfer policy remain with their consuming adapters and use cases. This update
ports the validated storage implementation from
[HRIS Mobile](https://github.com/fajarxfce/hris-mobile/commit/0267e71).
No starter installation or runtime performance measurement was performed.

## Inline loading

Root checks passed with 218 desktop tests; Android dev and production Web builds
passed. The compact loading regression keeps both its label and an adjacent
action visible at 200% text in a bounded viewport. The default loading layout
remains unchanged. Web retains its three existing bundler warnings. No starter
installation or runtime performance measurement was performed.
The same component and regression exist in
[HRIS Mobile](https://github.com/fajarxfce/hris-mobile/commit/f3b283e).

## Controlled date picker

Root checks passed with 217 desktop tests; Android dev and production Web builds
passed. The shared regression covers caller-owned selection, disabled dates/month
navigation, full-date accessible labels and 200% text at 320 dp. Calendar policy
and data acquisition remain with consuming features. This component also exists
in [HRIS Mobile](https://github.com/fajarxfce/hris-mobile/commit/e960e40).
No starter installation or runtime performance measurement was performed.

## Multiline text fields

The shared field keeps its single-line default and accepts a caller-selected
`maxLines` for longer input. Root checks pass with 216 desktop tests, including
line layout and newline-preserving editing at 200% text. Android dev and the
production Web build pass; Web retains its existing three bundler warnings.
This increment did not install the starter APK or measure runtime performance.

## Storage observer recovery

The 2026-10-12 dashboard correction passed root checks with 215 desktop tests,
all three dashboard presentation tests in Chromium, Android dev assembly and
the production Web distribution. The regression first reproduced a completed
storage source that remained disconnected after refresh. It now covers explicit
recovery, no automatic retry, replacement cancellation and disposal. Web retains
the existing three bundler warnings. This increment did not install the starter
APK or measure frame times or heap use.

## Replication metadata and navigation badges

The 2026-10-12 foundation update passed root checks, the database's Chromium
contract tests, and the Android dev build. Shared SQLite/IndexedDB tests cover
checkpoint observation during staging, publication, removal, account replacement
and cancellation. Navigation UI tests cover bottom/side badges, 200% text, full
accessible counts and retained tab actions. Application unread-count acquisition
and company policy remain in HRIS Mobile.

## Earlier foundation checks

Earlier Linux checks:

- Architecture checks for 33 modules, ktfmt and Detekt with no findings.
- 92 JVM tests: session restoration and revocation, concurrent refresh, cancelled
  and late results, secure-store failure recovery, account cache isolation,
  SQLite migrations/transactions, stale pagination, offline data, form validation,
  duplicate submissions, feature flags, notifications, workers and navigation.
- The assembled Compose UI covers onboarding, login, load more, flags, inbox,
  changing language and logout. Resource tests load both languages and plurals.
- 53 browser tests cover IndexedDB, DataStore, session policy, API error mapping,
  language resources, feature flags, workers and diagnostics.
- Android dev/staging debug and prod unsigned release APKs; Web distribution.

Gradle dependency verification is enabled using the committed SHA-256 metadata.
See [quality gates](quality.md) for updates and their trust boundary.

Screenshots from UI tests are under `apps/shared/build/reports/screenshots`.
The CI workflow also builds an unsigned iOS simulator application and Windows
classes. Compilation does not validate native credential-manager access, Apple
signing, Firebase delivery, or OS permission behavior on physical devices.

Earlier revisions were tested on Android 16 for notification access, Settings
recovery, local delivery/taps, FCM registration and WorkManager sync. Those device
checks were not repeated for the session/localization changes: the device was
unreachable. No deliberate crash was triggered and Crashlytics delivery has not
been verified in Firebase Console.

The demo transport runs in-process. Its tests verify client contracts and
idempotent replay behavior; a real backend still needs its own authentication,
refresh-token rotation, idempotency and conflict-resolution validation. Web
credentials are ephemeral; persistent browser authentication requires a backend
cookie integration.

## Reusable mobile layout components

Root architecture, formatting and distribution checks passed with all 198 desktop
tests. The shared header/action-tile regression covers a 360 dp layout at 200%
text, heading semantics, 48 dp icon actions and keyboard activation. Android dev
and Web distribution builds passed; Web retains the existing bundler warnings.
The corresponding components were exercised in HRIS Mobile against its real API.
This increment did not install the starter APK on a physical device, and no
frame-time or heap measurement is claimed.

## Search entry and focus ownership

The shared search entry opens a search surface without creating an editor. Optional
initial focus in `AppSearchField` follows its composition lifetime; subsequent query
updates do not reacquire dismissed focus. Root checks passed with 267 desktop tests,
two application Chromium tests and Android dev assembly. Regression coverage
includes pointer/keyboard activation, editor-free entry semantics, focus dismissal,
disposal and reopening. HRIS Mobile separately exercises navigation and real API
search on its Android emulator.


## Account-owned read replicas

On 14 October 2026, root checks passed with 279 JVM/desktop tests, 24 database
Chromium cases and Android dev assembly. Both database implementations exercise
stable account/API/company partitions independently of the current session,
logout and reauthentication, late writes, observer invalidation, checkpoint
compare-and-set, tombstones, cancellation and reopening. Migration tests retain
active/staging rows and equal-version deletions from Room schema 8 and IndexedDB
schema 7; the new versions are 9 and 8 respectively.

HRIS Mobile additionally validates encrypted Inbox integration and Android upgrade,
offline reads, and same-account sign-in recovery. The starter's generic contract
does not introduce an HRIS feed or change its demo's session-owned dashboard data.
No starter APK installation or new physical-device/performance claim is made.

## Indexed replica reads

The raw replica store supports bounded equality-tag queries. Multiple pages share
one published generation/revision and active account check; record, index and
cursor changes commit together. SQLite and IndexedDB tests cover filtering, ordered
pagination, status-index replacement, stale updates, deletion, rollback, staged
publication, observer disposal and replacement sessions. Both migration suites
retain untagged account ciphertext, pending generations and tombstones before
adding indexes.

Root checks passed with 283 JVM/desktop tests. The database (25) and secure-storage
(16) Chromium suites passed, including independent HKDF/HMAC vectors and cancellation.
Android dev assembly passed. This validates the storage foundation; it does not
claim that a business collection is connected to a change feed or that UI/device
acceptance has been performed for this increment.
