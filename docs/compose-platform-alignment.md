# Compose platform alignment

The starter follows validated HRIS Mobile caa59e487a1caa59a4b8b8ee46c0fd8e48dc25ad with Compose Multiplatform 1.10.3
and KMP/Android Lifecycle 2.10.0, retaining Kotlin 2.3.21. Mobile's official all-target Paging 3.5.3
required the compatible platform versions. HRIS Paging, Matrix, account policy and product UI
stay downstream. Starter dependencies were resolved independently; every introduced artifact
was fetched from official repositories and checked without changing prior hashes or policy.

Desktop Back fixtures now drive NavigationEventDispatcher 1.0.2 through the actual Compose
compatibility owner. The test source set declares that API explicitly because backhandler only
exposes it transitively at runtime. The Web lock updates only the two imported local Skiko workspaces to 0.9.37-4; external npm
packages and integrity values are unchanged, and the normal Gradle lock check validates them.
The same UI-test JUnit artifact is referenced through the
catalog instead of its deprecated plugin accessor. The app test host explicitly owns and
clears its lifecycle/ViewModelStore: Compose 1.10's outer test host otherwise lives until the
whole test ends. Existing back/cancel/fallback and retained-state assertions remain unchanged.
Production navigation and runtime ownership code are unchanged.

Independent verification freshly executes all 1049 prior cases: 547 desktop, 496 browser and 6
build-tooling cases. Three Android variants, lint, production Web, Apple source/test-source
compilation and quality checks pass. Inspector 742a2d1c remains pinned and unchanged. These
checks do not claim a new Apple runtime execution, phone installation or live deployment.
Source manifest: e893dbc05f23a71a82e8a4ecd5fe1ebf08beaa3976717a592b7c502654db2ed6.
Reports: 31da699662500c0a914d62ed408b956686dd4477ccdfd4b9385c14111674282c.
