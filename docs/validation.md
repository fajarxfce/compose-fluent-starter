# Validation

Local validation on Linux:

- Architecture checks for 25 modules and Kotlin formatting.
- 57 desktop tests covering cancellation, stale refreshes, permission recovery, pending deep
  links, DataStore corruption and persistence, actual SQLite operations, error boundaries,
  transactional outbox writes, migration, replay, worker retry/lifetime, and the assembled UI
  flow from onboarding through sign-in, inbox, and logout.
- Six browser tests for IndexedDB transactions/migration/reopen and worker scheduling/cancellation.
  CI also runs the three existing protobuf preference browser tests.
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
