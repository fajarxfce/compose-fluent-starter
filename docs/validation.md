# Validation

Local validation on Linux:

- Architecture checks for 22 modules and Kotlin formatting.
- 39 tests covering cancellation, stale refreshes, permission recovery, pending deep
  links, DataStore corruption and persistence, actual SQLite operations, error boundaries,
  and the assembled UI flow from onboarding through sign-in, inbox, and logout.
- Android dev debug APK build, plus Desktop and Web Kotlin compilation.

UI test captures are generated under `apps/shared/build/reports/screenshots`.
The CI workflow builds Android flavors, the Web distribution, an unsigned iOS simulator
application, and the Windows desktop classes. Platform compilation does not replace
physical-device notification, APNs/FCM delivery, or browser permission testing.

Firebase configuration and credentials are excluded from Git. CI builds without local
Firebase configuration; configured push delivery needs separate environment validation.
