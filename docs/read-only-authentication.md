# Authentication replay for read-only POST

An endpoint that uses POST solely to read may opt into
`forSession(sessionId, AuthenticationReplay.ReadOnlyPost)` at its API boundary.
After a401, the existing authentication adapter can refresh and replay the same
repeatable bytes once. The originating session and configured origin remain fixed;
a second401 is returned without another refresh. No command key or new header is
added. Ordinary POST/PATCH and streaming bodies retain their existing restrictions.
Do not select this mode for an endpoint that mutates state.

The supplied authentication use case owns refresh and session policy. The transport
adapter only observes the declared HTTP semantics. Cancellation during refresh
prevents replay. Endpoint business retries, provisioning and recovery stay outside
this generic module.

The two source/test files match accepted HRIS Mobile c1410b31 exactly. Its full local
gate passed6,096fresh cases, including core network browser tests. Starter's own
root, Android, browser and production web gate passed194fresh cases in64suites
in305.569seconds, with1,025unchanged inputs and zero failures/errors/skips/Detekt or
Android lint errors (44existing lint warnings). [Evidence](measurements/read-only-post-port-2026-11-07.json).
Apple CI now includes actual core network simulator tests; its first acceptance remains
pending. The prior worker port's first Apple run42331840556 completed before this port
was applied.
