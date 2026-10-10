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
