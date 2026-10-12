# Validation

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
