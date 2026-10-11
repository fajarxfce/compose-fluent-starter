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
