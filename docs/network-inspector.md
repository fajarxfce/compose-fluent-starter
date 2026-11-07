# Development network inspector

The Dev application opens a reusable inspector from Account → Settings → Network
inspector. Its core, Ktor adapter and Compose viewer live in the separate
[KMP Network Inspector](https://github.com/fajarxfce/kmp-network-inspector) repository.
This candidate uses a private, unpublished dependency; distribution must be resolved
before promotion to the primary branch. Do not copy private source into this repository.

Application composition owns one optional `NetworkInspection`. Ktor clients for the
public API, authenticated API, OIDC and Firebase resolve its current recorder for each
actual attempt. The default Starter backend remains an in-process demo transport;
seeing that request in the inspector does not imply that it crossed a network. The
Starter contains no Matrix SDK dependency or HRIS transport/account policy.

`ApplicationNetworkInspection` owns the session observation and its supervised job.
Changing the stable login/user identity closes and erases the old recorder. Token
refresh preserves it. Identity observation completion/failure and container disposal
close capture. A late request completion retains its original recorder, so it cannot
reappear after Clear or enter another account's capture.

The viewer renders bounded immutable snapshots. Navigation owns lifecycle collection,
filter and selection; a replacement recorder resets selected detail before numeric
request IDs can be reused. Back closes detail before leaving the viewer. The app supplies
Fluent colors, bounded layout and bottom safe insets. Feature ViewModels and repositories
perform no diagnostics I/O. Library controls currently use English; the app entry, title
and Clear action follow the selected language.

Capture includes method, bounded path/header metadata, status and timing. It retains no
request/response bodies, query values or credentials, and does not modify authentication,
retry, payloads, TLS or command outcomes. No capture is created by default outside Dev;
the application host can explicitly supply or omit one.

Build with the separately authorized checkout:

```sh
xvfb-run -a ./gradlew :check :apps:android:assembleDevDebug \
  :apps:android:lintDevDebug :apps:shared:wasmJsBrowserTest \
  :apps:shared:wasmJsBrowserDistribution :apps:shared:compileKotlinIosArm64 \
  -PnetworkInspectorDirectory=/absolute/path/to/kmp-network-inspector \
  --dependency-verification strict
```

The host port follows accepted HRIS Mobile source `52df4ee1` and installed build57.
Five source/fixture files are byte-identical. The session-lifetime fixture uses Starter's
own Session constructor; the Firebase module keeps its HTTP engine factory last so
existing trailing-lambda callers remain valid. The same correction passes two focused
Ktor/Koin integration cases in Mobile. Common tests cover replacement/logout, token rotation, dead
identity observation and disposal. Desktop UI tests cover Clear/ID reuse, platform Back
and large text in a small viewport. The independent complete Starter gate passes:
root checks, Android dev assembly/lint, actual browser tests, production web output,
iOS source compilation and quality checks. The final run took61.419s with10 fresh
browser cases in4suites, no failures/errors/skips and1,031 unchanged source inputs.
Earlier runs retain335 and87 fresh passing cases; their fixture compilation and
lockfile failures are preserved in the [exact evidence](measurements/network-inspector-port-2026-11-07.json).
Android lint has zero errors and44 pre-existing warnings.
Apple source compilation is not simulator/device runtime acceptance. Private repository
CI currently cannot start because of GitHub account billing; visibility is unchanged.
