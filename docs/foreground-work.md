# Foreground work and persistent scheduling

A platform host can observe `AndroidWorkScheduler` requests with `onRequested` and
signal the matching `ForegroundWorkScheduler.notify(key)`. The callback carries only
the task key. It runs after the caller has committed durable intent and before the
WorkManager enqueue, so an older OS retry does not postpone an active host's wake.
The host owns the selected keys, foreground coroutine lifetime and callback. Generic
scheduling never decides account, permission, message or network policy.

`notify` is nonblocking and conflated: unknown/closed lanes return false. A stopped
host retains at most one wake per key; restarting consumes it with the initial scan.
Wakes arriving during active work schedule one follow-up without concurrent writers.
Cancellation must finish before replacing the host. The platform's persistent worker
continues to provide recovery after process death; the signal does not store intent.

The four source/test files were copied exactly from accepted HRIS Mobile08545820.
Mobile passed7,436 fresh tests and a real Android WorkManager backoff/stop/restart
fixture. The fixture measured a synthetic wake, not chat delivery latency. HRIS task
selection and Android process lifecycle wiring remain in the application.

Starter's complete local gate passed in245.086seconds:90 fresh cases in29 suites,
zero failures/errors/skips,1024 stable inputs, zero Detekt findings and zero Android
lint errors(44 existing warnings). Root checks, worker browser tests, Android
compilation/lint and the production browser build passed. Apple CI now includes
actual worker simulator tests; first-run Apple acceptance is pending.
[Evidence](measurements/foreground-delivery-port-2026-11-07.json).
