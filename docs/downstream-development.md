# Downstream development

[HRIS Mobile](https://github.com/fajarxfce/hris-mobile) is an application generated
from this template. It exercises the foundation against a real backend and keeps
its own application history.

Reusable corrections to networking, MVI, storage, synchronization, design-system
components, resource ownership, and build conventions belong in this starter as
well. Reproduce the issue with a focused regression test, remove HRIS-specific
assumptions, and validate the change in both repositories. Link the corresponding
commits in their descriptions or delivery records. Do not copy credentials,
employee data, endpoint configuration, or business workflows into the template.

Template generation copies files once. It does not subscribe applications to
updates. Port subsequent changes through reviewed atomic commits; use a clean
cherry-pick only when the application code still matches. Never overwrite a
downstream tree or merge unrelated template histories to synchronize it.

Keep the template's default branch on the latest verified foundation. Preserve
platform-specific checks and report separately which behavior was verified by
unit tests, an actual backend, or a device.

## Reviewed foundation updates

The Android convention enables core library desugaring for the API 24 minimum,
matching [HRIS Mobile 76c6375](https://github.com/fajarxfce/hris-mobile/commit/76c6375).
An API 24 emulator reproduced the missing Java time class before that correction
and opened the employee calendar afterward. Application-specific calendar behavior
remains in HRIS Mobile.

The date-only formatter and timezone regression match
[HRIS Mobile 30b51fc](https://github.com/fajarxfce/hris-mobile/commit/30b51fc).
They keep a calendar date independent of the device timezone while applying the
selected language. Both repositories passed root checks and Android/Web builds;
HRIS Android emulator captures cover English and Indonesian dates. Apple builds
remain part of platform CI.

Reusable screen headers, action tiles and page-spacing controls now support the
employee layouts in HRIS Mobile. Their shared regression checks 200% text,
heading semantics, one action per tile and keyboard activation. The starter
receives the components and regression; company switching, inbox and calendar
screen layouts remain in HRIS Mobile.

Foreground location matches
[HRIS Mobile 21eead1](https://github.com/fajarxfce/hris-mobile/commit/21eead1),
including Apple callback ownership at
[b90f908](https://github.com/fajarxfce/hris-mobile/commit/b90f908).
The shared foundation contains raw Android/iOS adapters, a domain repository,
DTO/failure mapping and cancellation tests. Attendance permission policy, company
access, capture evidence, encrypted commands and review remain in HRIS Mobile.

Optional `AppCard` content spacing matches
[HRIS Mobile a871170](https://github.com/fajarxfce/hris-mobile/commit/a871170).
Its zero-spacing default preserves existing callers. Both repositories passed
root checks and Android/Web builds; attendance card layouts stay in HRIS Mobile.

Fixed page bars, adaptive menu grids, text actions and preference rows match
[HRIS Mobile bb1cc5c](https://github.com/fajarxfce/hris-mobile/commit/bb1cc5c).
Shared UI tests cover scroll-independent back/refresh actions, 200% text, keyboard
activation and radio/switch semantics. Both repositories passed root checks and
Android/Web builds. Home attendance, company switching and employee service
admission remain in HRIS Mobile.

Settings uses the same fixed back action and language/security preference rows as
[HRIS Mobile 99b3cce](https://github.com/fajarxfce/hris-mobile/commit/99b3cce).
The starter retains its account roles, permission list and access refresh action.
Its assembled UI test covers language changes, persistence and back navigation.

Root-tab Back handling and single-line navigation labels also support the
[HRIS Mobile navigation update](https://github.com/fajarxfce/hris-mobile/blob/main/docs/template-updates.md#root-navigation).
Shared regressions cover inner-page return, cancelled gestures, rapid Back during
the root transition, platform fallback and full accessible labels at 200% text.
Employee directory policy, Inbox integration and service admission remain in
HRIS Mobile.
Both repositories passed root checks and Android/Web builds. HRIS Android
emulator checks also reproduced and verified the rapid-Back correction.

## Month selection

The controlled `AppMonthPicker`, optional filter-chip icons, and `formatMonth`
match [HRIS Mobile f531200](https://github.com/fajarxfce/hris-mobile/commit/f531200).
The picker owns layout and selection semantics; callers supply the available
months, years, labels, and actions. Tests cover disabled choices, 200% text,
English/Indonesian names, and timezone-independent formatting. Root checks,
the localization Chromium suite, Android dev, and production Web builds passed
in both repositories. Attendance rules and screen state stay in HRIS Mobile.

## Storage observer recovery

Dashboard subscription recovery matches
[HRIS Mobile eee789e](https://github.com/fajarxfce/hris-mobile/commit/eee789e).
A storage source can report a failure and complete while its outer session
observer remains active. Successful refresh, pagination and saved-item writes
reopen the owned content subscription; a failed operation does not trigger an
automatic retry loop. Cancellation checks reject obsolete results before state
changes or subscription replacement. The starter regression reproduces the
completed-source failure and verifies cancellation, recovery and disposal.

## Multiline text fields

Optional multiline input matches
[HRIS Mobile 844314c](https://github.com/fajarxfce/hris-mobile/commit/844314c).
`AppTextField` retains its single-line default; a caller can supply `maxLines`
without replacing the shared field or owning keyboard internals. Both repositories
passed root checks and Android/Web builds. The shared regression checks line layout
and newline-preserving editing at 200% text. Leave reasons, validation and command
orchestration remain in HRIS Mobile.
