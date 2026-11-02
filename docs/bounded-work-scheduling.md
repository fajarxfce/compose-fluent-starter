# Bounded Android work scheduling

Ordinary one-time keys keep APPEND_OR_REPLACE behavior. Applications may opt fresh
registered task keys into two independent WorkManager slot names. Each name has
at most one unfinished request under KEEP; there is no dependent work chain. The
query reads only ENQUEUED/BLOCKED/RUNNING states for those two names.

The caller must commit a durable wake BEFORE every enqueue attempt. A waiting slot
then provides a future start that can read that committed wake. If all observed
slots are running, the scheduler fills an empty slot; it never targets an observed
running slot. A competing fill of the same empty slot also starts after the earlier
durable write, and KEEP preserves that work. Each request has a fresh random ID.
Two occupied running slots report failure instead of adding a third request.

One application scheduler owns at most32 opted-in task keys and32 admitted callers,
with separate per-key mutexes. Admission failure, cancellation or platform failure
leaves the durable checkpoint for explicit recovery. Startup/reconnect/periodic
owners must repair persistence/enqueue gaps. Acknowledgment must compare the
checkpoint read before processing; old completion cannot erase a newer request.
No successful enqueue promises immediate execution or successful synchronization.

Two OS jobs may run simultaneously for an opted-in key. Its application task must
serialize processing or return a retained retry without duplicate native ownership.
Periodic work is separately named/bounded. WorkManager input remains the task key;
network constraints and exponential30s backoff remain. Existing application task
keys are not automatically migrated or opted in by this generic capability.

Validation must include common admission/cancellation tests AND actual Android
WorkManager/SQLite execution, competing scheduler instances, retry reuse, failed
and cancelled slots, generation fencing and process-death repair. The initial
proposal to track APPEND_OR_REPLACE chains by stable tags was rejected before
application: WorkManager can delete the full WorkSpecs and their tags. Independent
slot names enforce the bound without replacing a running chain.

[Validation](measurements/bounded-work-2026-11-02.json) records the exact Mobile
port and independent Starter root/worker-browser/Android/lint/Web checks. Mobile's
separate synthetic emulator proof exercises actual WorkManager/SQLite, two competing
scheduler instances, generation fencing and process-death repair. Starter's demo
configuration remains independent and no task opts in automatically. The companion
Apple wake-store fixture passed Mobile hosted execution before copying; its own
Starter hosted result is tracked separately in the same measurement.
