# Notifications and deep links

Open **Account → Notifications** to request OS access, send a local test, or copy the
FCM token. Notification permission is requested only after an explicit action.
The screen checks access again when resumed, including after a change in Settings.
The inbox observes the database reactively and survives process restarts.

## Ownership

- `core/notifications/domain`: messages, repository contracts, permission and delivery use cases.
- `core/notifications/data`: raw platform adapters, DTO mapping, and `safeNotificationCall`.
- `core/database`: the inbox storage contract, Room/SQLite, and IndexedDB.
- `features/notifications/presentation`: MVI inbox screen and feature navigation.
- Platform runners: SDK callbacks, Activity registration, resource lifetime, and incoming URLs.

`ReceiveNotification` saves before displaying. Denied OS permission does not discard
received messages. `systemDisplayed` records a message already handled by the OS without
posting it again. Messages use their delivery ID as the database key; a repeated ID
updates the existing entry. This is a local inbox, not a server-synchronized notification
history or an exactly-once delivery guarantee. Add an authenticated inbox endpoint if
history must include messages the platform never delivered to application code.

## Internal destinations

Local display accepts bounded internal paths of ASCII letters, digits, hyphens and
underscores, separated by slashes. A path has at most 12 segments, 80 characters per
segment and 512 characters total. Schemes, hosts, query/fragment parts, encoded or
empty segments are rejected before platform display. Android appends each segment
separately, so hierarchical resource links keep their path structure. Its full
message ID identifies notification replacement, with only one alert on update.
The consuming application still validates the destination against its current
session and resource authorization when the user opens it.

## Token retirement

`PushTokenRepository.deleteToken()` removes the SDK registration on Android,
iOS and Web without requesting OS permission or acquiring a replacement token.
The Swift bridge exposes Firebase's deletion callback. Failure and cancellation
remain visible to the caller; missing provider configuration is not reported as
successful deletion.

Android/iOS token callbacks invalidate the current SDK value. Each observer reads
the current token and repeated callbacks with the same value are deduplicated.
The adapter no longer keeps token strings in a replay buffer, so a callback from
before deletion cannot replace a new session's acquired token.

The consuming application owns backend registration/disable, persistent retirement
intent, session binding and retries. Stop its token observer before retiring a
registration. Cancelled SDK awaits reject late results; cancellation does not prove
that an external SDK operation rolled back. Reconcile retirement before registering
a token under another account. Do not include tokens in diagnostics.

## Android / Firebase

Register each application ID from [environments](environments.md) in Firebase.
Place a shared multi-client `google-services.json` in `apps/android/`, or use separate
files under `apps/android/src/dev/`, `src/staging/`, and `src/prod/`. All are gitignored.
Builds without a configuration support local notifications and report FCM as unavailable.

The Android SDK uses its compatible token-addressing API, matching iOS/Web and Firebase
Console tests. The SDK's optional FID-addressing registration mode is not enabled; adopting
it requires a coordinated backend migration.

Firebase initializes through its Android provider. `StarterMessagingService` receives
foreground messages and data-only background messages, then calls the domain use case.
`onNewToken` publishes refreshes through the token datasource; a future backend integration
can consume `PushTokenRepository.observeTokens()` through its own use case. Do not treat
a registration token as an account identity or log it in production.

For a Firebase Console test, allow notifications and select **Copy push token**. Paste
it into **Messaging → Send test message**. Optional custom data: `destination=inbox`,
`activity`, `account`, or `overview`.

An HTTP v1 data payload, sent by a trusted server using its own credentials:

```json
{
  "message": {
    "token": "DEVICE_FCM_TOKEN",
    "data": {
      "id": "update-123",
      "title": "Update available",
      "body": "Open the activity feed.",
      "destination": "activity"
    },
    "android": { "priority": "high" }
  }
}
```

Android displays notification payloads itself while the app is backgrounded; those
messages do not reach `onMessageReceived` and are not automatically in the local inbox.
Use data-only payloads for app-owned persistence/display. Force-stopped apps and OS
restrictions can prevent delivery. Firebase server credentials never belong in the app.

## iOS / Firebase

FirebaseCore and FirebaseMessaging use Swift Package Manager in the Xcode runner.
Place each plist at `apps/ios/Configuration/<environment>/GoogleService-Info.plist`.
The build checks its bundle ID before copying it into the application. A missing plist
leaves local notifications available and FCM unconfigured.

For physical-device push, configure a signing team/profile with Push Notifications,
and upload an APNs authentication key or certificate in Firebase project settings.
The runner has the APNs entitlement and explicitly forwards the APNs token. Firebase
method swizzling is disabled. Debug builds use APNs development; Release uses production.

The OS displays remote notifications. Foreground receipt and notification taps save
messages to the local inbox. Background notifications that are never opened are not
fetched into local history. This starter does not implement silent background sync.

## Web / Firebase

Local notifications and FCM require a supported browser, HTTPS (or localhost), and
permission. Create a Firebase Web app and a Web Push certificate/VAPID public key, then
copy `apps/shared/config/firebase-web.example.json` to
`apps/shared/config/<environment>/firebase-web.json` and fill its values.
The convention plugin generates the page/service-worker resources for that environment.
Do not edit generated `firebase-config.js`; it is public client configuration, not a place
for private keys. A page without configuration still runs and supports local notifications.

Foreground push is persisted through the shared use case. The service worker displays
background push and opens the appropriate route. Background messages are not written to
the app's inbox until a server synchronization feature is added. Deploy the full web
output together, including the worker and generated configuration resources.

## Desktop

Local notifications use the system tray where supported. Clicking the current tray
notification opens its destination. FCM is unavailable on desktop. Linux desktops without
a compatible system tray return an unavailable capability instead of claiming delivery.

## Deep links

Allowed destinations: `overview`, `activity`, `account`, `inbox`. Unknown paths, query
parameters, and links for another environment are ignored. Protected destinations wait
until onboarding and sign-in complete, the host resumes and its destination finishes
entering. A waiting intent binds to its first session and is discarded on logout or
session replacement. An older acknowledgement cannot consume a different pending
intent. Signing out removes the protected stack. Pending links are in-memory intents
and do not survive process death.

```sh
adb shell am start -W -a android.intent.action.VIEW \
  -d 'fluentstarter-dev://app/inbox' dev.fajar.fluent.starter.dev
xcrun simctl openurl booted 'fluentstarter-dev://app/inbox'
./gradlew :apps:shared:run --args='fluentstarter-dev://app/activity'
```

Web uses the current deployment URL followed by `#/inbox`, for example
`http://localhost:8080/#/inbox`. Native desktop protocol registration is installer-specific;
the starter accepts a URL as its launch argument. Custom mobile schemes are registered.
Verified HTTPS App Links / Universal Links require a domain you own, Android asset links,
and Apple's association file and entitlement; they are not claimed by this template.
