# Firebase provider foundation

The identity module supplies bounded Firebase email/password REST and Android
Google Credential Manager adapters. The provider returns opaque Firebase proof;
it never writes an application session or retains a Firebase SDK session/refresh
credential. The starter's demo sign-in continues to use its existing demo backend.
Applications integrate this provider with their own server exchange and account
authorization policy.

## Host configuration

Android reads `project_id`, `google_api_key` and optional `default_web_client_id`
from Google Services resources. The host supplies its package and current APK
certificate fingerprint for Android-restricted API keys. Register each package and
signing certificate in the intended Firebase project, enable the desired Auth
providers and install the matching ignored `apps/android/google-services.json`.
Google needs the public web OAuth client ID in that configuration. No client
secret belongs in the application. See [notifications](notifications.md) for file
setup; FCM delivery credentials and Firebase Auth are separate concerns.

The Android Application owns `AndroidGoogleCredentialSource`; each Activity
attaches and detaches it. The source keeps a weak Activity reference. Each request
uses a fresh nonce and owns a cancellation signal. Google token callbacks must
match that nonce and the requested audience, and the provider interaction closes
before Firebase's HTTP exchange. Only Firebase's server validates Google's token
signature. Google dismissal is a cancelled result; coroutine cancellation propagates.
Late or duplicate callbacks cannot complete a disposed interaction.

Other hosts default to unavailable sources. `createAppContainer` accepts explicit
`FirebaseClientOptionsSource` and `GoogleCredentialSource` implementations; a host
that supplies them must own its UI attachment and the container's lifetime. The
container closes Google resources and its lazily acquired, dedicated HTTP client.

## Application integration

An application use case coordinates `FirebaseAuthenticationRepository` with its
own backend exchange repository. It must bound and validate input, serialize
interactive attempts, set a total deadline, propagate cancellation and verify the
installed project's compatibility with backend configuration before opening a
provider. Retain a stable intent for explicit retries and check current session
ownership after every acquisition. Publish only server-issued application
credentials atomically; provider email/claims must not grant application access.

The provider fixes Google's HTTPS endpoint, bounds responses, maps sanitized error
codes and sends no application authorization, cookie or internal tracing context.
Account-link confirmation fails instead of silently linking provider accounts.
The adapter performs one request per explicit attempt and never retries automatically.
Product copy and backend-specific errors belong to the consuming application.

## Provenance and acceptance

This foundation comes from Mobile `d191c61d`. Mobile's real Android acceptance
completed Firebase password authentication, HRIS exchange and MFA using a temporary
provider account, then removed it. Google system sign-in launch and dismissal also
passed; actual Google token exchange and iOS remain separate acceptance. See the
[Mobile validation record](https://github.com/fajarxfce/hris-mobile/blob/880f2a46/docs/validation.md).
HRIS native sessions, account binding, MFA, login discovery and HRIS screens remain
in Mobile. See [downstream development](downstream-development.md) for port checks.
