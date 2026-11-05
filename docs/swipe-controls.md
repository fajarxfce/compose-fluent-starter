# Swipe tabs and floating actions

`AppSwipeTabContent` complements the controlled `AppTabs` header with horizontal
gestures. Supply one to eight uniquely keyed pages, the selected value, a selection
callback and a content slot. Stable keys retain each page's rendering state.
The slot receives whether its page is settled and active; navigation owners must
keep acquisition and effect consumption inactive for prefetched neighbours.
Settled inactive pages are excluded from accessibility. Explicit selection can
interrupt a gesture, and a new gesture can interrupt the selection animation.
Disabled gestures still honor external selection. Reduced motion skips animation.

`AppFilterButton` offers a 48dp icon disclosure beside a search field. Supply its
localized accessible label and selected value. Selection changes its treatment
and state description; the feature owns the filter surface and choices.

`AppFloatingActionButton` is a 56dp action with a supplied icon and accessible
label. Its interruptible fade/slide uses the application motion setting, and an
exiting action stops accepting clicks immediately. The semantic `onAccent` token
keeps its icon legible in both themes.

`AppFloatingActionLayout` provides content and floating-action slots. It observes
consumed vertical user scroll without consuming it: 12dp downward travel hides the
action, upward travel restores it, and horizontal gestures have no effect. A new
`stateKey` resets action visibility. Callers own placement, safe-area insets and
enough list padding to let the last row clear the overlay. The layout holds only
rendering mechanics; it does not fetch data, select tabs or own navigation.

These four components, one color file and seven generic Compose UI cases are
copied exactly from HRIS Mobile `2539c6585f77c4f6898ff28426604c3583927fa4` after its
actual Android filter/swipe/scroll/offline proof. Its 3,088-case local gate passed.
HRIS directory policy, routes, localization and feature lifecycle wiring remain in
Mobile.

Independent Starter root checks, Android dev build/lint and production Web pass in
327.311s on unchanged inputs:390 desktop cases/125 suites and six build-logic cases,
zero failures/errors/skips.69 cases are fresh, including all seven new UI cases;
other reports are from unchanged up-to-date tasks. Lint has zero errors and44
existing warnings. The APK was built, not installed. No pixel-baseline or frame-time
claim. [Source and gate evidence](measurements/swipe-controls-port-2026-11-05.json).
Independent hosted Apple compilation and complete simulator app remain required.
