# HTTPS application links

Optional public registration files enable HTTPS App Links and Universal Links for
one exact DNS host and environment. Missing configuration keeps HTTPS admission
disabled; existing custom-scheme and browser-fragment routes remain available.
The path is `/app/<environment>/<route>`, for example
`https://links.example.com/app/dev/inbox`. The route after the environment uses
the [Starter route contract](notifications.md#deep-links): `overview`, `activity`,
`account` and `inbox`. Consumers own any additional route/resource policy.

## Configuration and generated artifacts

Use `config/app-links/dev.json.example` as the shape for ignored `dev.json`,
`staging.json` or `prod.json` in that directory. Replace its example host,
fingerprint and Apple application ID with the selected registration before use.
The files contain public registration metadata only. Unknown fields, non-UTF-8,
files over 16 KiB, unknown/duplicate environments, wildcard/IP/credential/port
hosts, wrong app IDs and malformed/duplicate fingerprints fail the build.

Android registration is required and accepts one to eight uppercase SHA-256
certificate fingerprints for that environment's exact application ID. Obtain them
from the actual signing certificate. A Play-installed build uses its Play App
Signing certificate, which can differ from the upload key. Do not substitute the
Firebase Android SHA-1 value. Optional `ios.applicationId` is the ten-character
Apple application-identifier prefix followed by the exact bundle ID. Supply the
value from the app's signing profile; omit the iOS entry until it is available.

```sh
./gradlew :core:common:generateAppLinks
./gradlew :apps:android:assembleDevDebug
```

`-PappLinksConfigDirectory=/absolute/path/to/public-config` selects a separate
registration directory consistently for generated Kotlin and Android manifests.
For Xcode, export `ORG_GRADLE_PROJECT_appLinksConfigDirectory` with that path so
both its preparation and framework Gradle invocations use the same input.

The generator owns `core/common/build/generated/app-links`:

- `kotlin/`: exact environment-to-host runtime admission.
- `ios/<environment>.entitlements`: existing push entitlement plus the configured
  associated domain when the iOS registration exists. Unconfigured builds have no
  associated-domain key; the expected bundle ID is checked by the Xcode pre-action.
- `sites/<host>/.well-known/assetlinks.json` and `apple-app-site-association`:
  combined public Android/Apple registrations for that host. Apple path components
  restrict each app to its environment prefix.

All registrations validate before replacing the task's owned output. Removing or
changing a registration removes obsolete generated host files and entitlements.
It does not delete files already deployed on a web server; update those explicitly.
The Android convention transforms each variant's merged manifest, adding one
separate auto-verified HTTPS intent filter to its launcher. Custom links and the
OAuth callback filter retain their separate scheme/host restrictions.

Every checked-in Xcode scheme prepares generated entitlements before building.
The app forwards `NSUserActivityTypeBrowsingWeb` through the same owned link
channel as ordinary custom URLs. No new navigation or credential owner is created.

## Admission and deployment

The shared parser bounds input to 4,096 printable ASCII characters and accepts only the exact
configured HTTPS authority and case-sensitive environment prefix. Explicit ports,
credentials, trailing-dot hosts, queries, fragments, encoding, traversal, empty
segments and control characters are rejected. The application then admits known
routes. A URL never grants access or signs a user in. Existing onboarding,
session admission, resumed-host delivery and pending-link ownership rules apply.
Session replacement/logout discards the previous session's waiting destination.

Serve both association files at their exact `https://<host>/.well-known/...` paths
with HTTP 200, JSON content type, valid TLS and no authentication or redirect.
These paths must bypass the dashboard SPA fallback. A consuming product supplies its public browser handoff when the OS stays in
the browser. Starter's web application keeps its existing `#/...` route format;
this generic port does not include the HRIS dashboard landing page.
Keep the selected domain, certificates, signing profile and deployed files in the
environment's deployment record. Generation alone does not prove DNS/TLS, OS
domain verification or APNs delivery.

## Consumer identity and validation

The default application base ID is `dev.fajar.fluent.starter`; dev/staging append
`.dev`/`.staging`. When renaming a generated application, align the Android
convention's application ID, `baseApplicationId` in `starter.environment.gradle.kts`
and all Xcode bundle identifiers. The Xcode preparation step rejects mismatches.
`prepare_app_links.sh` uses the selected `FLUENT_ENVIRONMENT`; all four schemes and
eight signing configurations reference the generated per-environment entitlements.

The source foundation at Mobile `d36a1bc9` passed all four full CI jobs in
[40887772486](https://github.com/fajarxfce/hris-mobile/actions/runs/40887772486),
including 103 native cases in 28 downloaded suites and the complete configured
Xcode build. Actual downstream Android cold/login/company/scope/offline/archive
acceptance also passed before porting. Nine portable files followed that source;
the two parser files additionally
include Mobile `72341ef6`, proved by JVM/Chromium before copying, to reject Unicode
case-folding aliases. The Apple runner retains Starter identity, and the route resolver keeps only Starter
routes. HRIS backend, company/resource policy and private configuration stay in
the consuming application.

Starter has separate local and hosted validation recorded in
[the handoff](agent-handoff.md) and [measurements](measurements/app-links-2026-10-29.json).
Configured checks use an owned fictional-domain fixture and never publish DNS,
TLS, Apple/Android association files or a release. Physical iPhone tests were
waived by the user for this completion pass; unsigned builds do not claim APNs or
actual device sign-in/link acceptance.

## Final accepted verification

Final Mobile source `72341ef6` passed all four jobs in full
[40895095518](https://github.com/fajarxfce/hris-mobile/actions/runs/40895095518).
Downloaded Apple reports confirm 104 cases in 28 suites, zero failures/errors/skips
and the complete Xcode app; compilation/tests/Xcode took 701s/77s/1040s.
Starter port `738db36` independently passed all three jobs in full
[40895798634](https://github.com/fajarxfce/compose-fluent-starter/actions/runs/40895798634),
with 84 Apple cases in 24 downloaded suites, no failures/errors/skips and Xcode;
compilation/tests/Xcode took 561s/84s/703s. Generated Android/Apple association
checks passed, and the Xcode/Apple artifact success markers were inspected.
No CI or owned local acceptance remains active from this increment.
