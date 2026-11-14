# Independent retained navigation stacks

Each concurrently retained navigation stack can supply a stable ownerKey to
rememberScopedNavigationState. The key isolates its NavigationViewModels store, controller and
saveable composition identity under the parent window. Existing no-argument callers keep the
default ViewModel key. Account/session changes still use ScopedNavHost scope; allocating a new
owner key for every account would retain obsolete stores until window destruction.

Three tests follow the exact accepted Mobile navigation fixture. Two controllers use identical
route types but independent owners. Scope replacement disposes only that stack's detail. Recreating
composition preserves both details, counters and models. A fresh window restores only the matching
admitted stack. Final window destruction clears every original model exactly once. The fixture
captures independent saved values before old-owner cleanup, rather than retaining mutable Compose
holder maps across simulated process destruction.

Only the generic function change and tests are ported. Starter retains saved-state format1;
Mobile's format3 tracks its own route compatibility. No HRIS discovery, Group ownership, Matrix
integration, dependency update or product route activation is included.

The independent root/platform result is recorded in the linked JSON. It preserves all1,049 prior
test identities and adds three desktop cases. Android variants/lint, production Web, affected app
browser tests and Apple source/test-source compilation are checked. Actual new Apple runtime and
phone delivery are not claimed. Pinned Inspector742a2d1c stays unchanged, including its pre-existing
gradlew.bat line-ending difference.

[Exact validation](measurements/navigation-stack-identity-port-2026-11-14.json).

Mobile source: https://github.com/fajarxfce/hris-mobile/commit/21435d2dccac25528c3d28b30f2605973cd4ff6e
