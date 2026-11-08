# Development network inspector

The Dev application opens a reusable inspector from its silent Android notification
after network activity, or from Account → Settings → Network inspector. Its core, Ktor adapter and Compose viewer live in the separate
[KMP Network Inspector](https://github.com/fajarxfce/kmp-network-inspector) repository.
The library source is public with owner approval; its package remains unpublished.
Distribution must be resolved before promotion to the primary branch. Keep its source
in the separate library repository.

Application composition owns one optional `NetworkInspection`. Ktor clients for the
public API, authenticated API, OIDC and Firebase resolve its current recorder for each
actual attempt. The default Starter backend remains an in-process demo transport;
seeing that request in the inspector does not imply that it crossed a network. The
Starter contains no Matrix SDK dependency or HRIS transport/account policy.

`ApplicationNetworkInspection` owns passive published-identity observation and its
supervised job. Starting diagnostics cannot acquire credentials or activate storage.
See [published session identity](published-session-identity.md).
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
CI currently cannot start because of GitHub account billing. The separate public library
passes 213 portable and 69 Apple cases at 6433dc3; this does not establish Starter
application runtime on Apple.


The optional Android adapter has one process-owned lifetime and an explicit immutable
Activity PendingIntent. Activity resume attaches it after background-only identity
restoration. Normal navigation gates consume the latest retained tap when the graph
is ready. Closing its owner prevents later taps from reviving it. Clear, capture
replacement and disposal remove the OS shortcut. Initial empty capture after process
restart preserves an existing shortcut without creating a new empty notification.

The Mobile consumer independently passed six actual Android OS scenarios and warm/cold
notification taps in build 60 before this port. Starter keeps its own namespace and
Session fixture; no Matrix SDK or HRIS delivery policy is introduced. The independent Starter gate passes 440 fresh cases in 138 suites with no failures,
errors or skips: root checks, Android assembly/lint, actual browser tests, production
web output, iOS source compilation and quality checks. It took 888.607 seconds on
1,042 stable source inputs and 49 stable library inputs. Lint has no errors and 45
dependency-version advisories. [Exact checks and first failure](measurements/inspector-notification-port-2026-11-08.json).
