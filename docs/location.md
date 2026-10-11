# Foreground location

`core/location/domain` provides `LocationRepository`, access status and a single
`LocationFix`. `core/location/data` contains raw platform adapters, mapping,
`safeLocationCall` and one Koin module. No feature requests permission at startup.

Android uses the OS location manager; its Activity attaches and detaches the
permission launcher. The source holds a weak Activity reference. iOS uses Core
Location. The app host closes the source, and caller cancellation stops GPS.
A cancelled permission request retains its callback slot until the OS responds,
so an old response cannot resume a newer request.
The iOS source retains its fix until the suspended call finishes cleanup; a late
callback cannot stop a new acquisition. Its simulator regressions use an injected
SDK manager to cover reentrant acquisition and closure during a pending fix.

Use cases coordinate permission and acquisition through `LocationRepository`.
The consuming use case owns timeout, freshness, fallback and retry policy.
`OpenLocationSettings` is an explicit user action; safe calls never request
permission or open Settings. A returned access status is interpreted by the use
case, while repositories map DTOs and exceptions without hiding application policy.

Only foreground permission is declared. Desktop and browser adapters currently
return unavailability; no location is fabricated. Add a raw platform adapter when
those hosts need location. Android/iOS platform integration is provided, but
physical device behavior must be validated by the consuming application.
