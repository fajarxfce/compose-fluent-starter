# Validation

Local validation on Linux:

- Architecture checks for 27 modules and Kotlin formatting.
- 72 desktop tests covering cancellation, stale refreshes, permission recovery, pending deep
  links, DataStore corruption and persistence, actual SQLite operations, error boundaries,
  transactional outbox writes, migration, replay, worker retry/lifetime, and the assembled UI
  flow from onboarding through sign-in, inbox, and logout.
- 19 browser tests for feature flag policy, cache/error/cancellation behavior, unconfigured Web
  Remote Config, protobuf compatibility, and preference persistence. CI also runs the six
  existing IndexedDB and worker browser tests.
- Android dev/staging debug and prod release APK builds, plus the Web distribution.
- Android 16 device: onboarding-gated deep link, denied/granted notification access,
  local delivery, notification tap, recovery after changing access in Settings, and
  Firebase registration token acquisition.
- Chrome smoke: onboarding, sign-in, pending inbox link, local notification delivery,
  and persisted preferences/inbox after reload.

UI test captures are generated under `apps/shared/build/reports/screenshots`.
The CI workflow builds Android flavors, the Web distribution, an unsigned iOS simulator
application, and the Windows desktop classes. Platform compilation does not replace
physical-device notification, APNs/FCM delivery, or browser permission testing.

Firebase configuration and credentials are excluded from Git. CI builds without local
Firebase configuration; configured push delivery needs separate environment validation.

Local-first validation:

- SQLite and IndexedDB v1-to-v2 migrations preserve existing inbox records.
- An Android 16 WorkManager task completed successfully after saving an activity. The local
  preference remained saved and the outbox was empty after acknowledgement. The database
  retained that preference across process restart and APK replacement.
- Chrome: dashboard cached, a saved preference committed and acknowledged, then retained after
  reload and sign-in. No page errors occurred in the completed smoke test.
- A DI integration assertion verifies that generated ViewModel bindings forward the requested
  initial dashboard tab, rather than silently using the overview default.

Physical iOS background execution and OS scheduling timing have not been tested here. The
in-process demo transport validates the client flow; these checks do not establish a real
backend's idempotency, conflict-resolution, or cross-device behavior.

Feature flag validation:

- Default/remote/override precedence, explicit false overrides, production restrictions,
  malformed parameters, and removal of remote parameters.
- Concurrent refresh serialization, cancelled waiters, late SDK completion, retained cache
  after failure, storage failure classification, fetch intervals, and clock rollback.
- The assembled desktop UI reacts to a persisted override and restores save controls after
  removing it. The action use case rejects a newly disabled flag even if a prior UI event
  still requests a save. ViewModel disposal stops flag observation.
- Android dev APK and browser distribution compile with the new SDK adapters. No Firebase
  Console values were published, and a configured Remote Config fetch was not exercised.
  The Android device was unreachable during this change; no new device validation is claimed.
