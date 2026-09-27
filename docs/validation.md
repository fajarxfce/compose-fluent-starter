# Validation

Local validation on Linux:

- Architecture checks for 22 modules and Kotlin formatting.
- 39 tests covering cancellation, stale refreshes, permission recovery, pending deep
  links, DataStore corruption and persistence, actual SQLite operations, error boundaries,
  and the assembled UI flow from onboarding through sign-in, inbox, and logout.
- Three browser tests for protobuf persistence, reactive updates, corruption, and cancellation.
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
