# Mobile experience

Use Fluent visual language with a mobile-first layout. Keep navigation, wording,
and actions direct. Company identity and the current employee scope must remain
clear without turning the phone screen into a compressed administrative portal.

- Use compact headers, readable lists, clear grouping, generous touch targets,
  and a small set of stable bottom-navigation destinations. Selected items use
  color and emphasis rather than a separate card.
- Preserve each tab's position, keep keyboard and system insets correct, and
  prevent a response from moving controls while the user is interacting.
- Use brief page transitions, selection changes, and inline feedback. Motion
  should explain a change of context or state, usually within 120–180 ms. Avoid
  decorative loops, large bounces, or staggered animation on every list row.
- Honor reduced motion and system animation settings. Completion, errors, and
  pending synchronization must remain understandable without animation.
- Replace account/company content immediately when its authorization scope changes;
  do not retain private outgoing content for a transition.
- Keep existing content during refresh. Reserve initial loading placeholders
  for missing content, and show an explicit recoverable error if loading fails.
- A queued offline action is visibly pending, not confirmed. Retain the command
  and its outcome in state/storage independently of a transient success effect.
- Lifecycle-aware route collectors own effects. Animations, timers, and input
  listeners must stop with their owners; no network or database work belongs in
  composition or animation callbacks.

Check light/dark contrast, text scaling, screen-reader labels, focus, keyboard,
touch targets, tab state, and interrupted transitions. Build/test results do not
establish frame-time or memory performance; record actual device observations
and measured workloads separately.

`AppListRow` provides one accessible action, bounded title/summary text, optional
metadata, and clipped press feedback. Place dates below the summary when a
trailing column would crowd a narrow screen. `AppLazyPage` accepts an owned list
state so paging can reset its position without rebuilding the route owner.

Put `AppTopBar` in the fixed `topBar` page slot, never inside scroll content. Use
16 dp content margins for inner mobile pages; page padding must not displace the
bar or duplicate the host's system insets. Use grid icons for small service menus,
icon actions for back/refresh, and text actions for secondary navigation. Mutually
exclusive preferences use radio rows; boolean preferences use switches. Settings
keeps its back action above the language, security and account-access groups.

## Navigation badges

Navigation items accept a composable badge slot. `AppCountBadge` displays counts
above 99 as `99+`, retaining the full localized accessible description. Zero and
unknown counts have no badge. Feature entry points own count observation; the
navigation components own only its layout in bottom bars, rails and sidebars.

## Search and filters

Use `AppSearchField` for compact search, retaining its accessible label and clear
action; use `AppTextField` for labelled forms. `AppFilterChip` opens a filter
surface. `AppDialog` owns only window presentation and delegates focus/dismissal
to the platform. Feature MVI owns visibility, selection and cancellable I/O.

Shared UI tests cover large text, clear/disabled actions, keyboard submission,
filter selection and dismissal. These controls were exercised in HRIS Mobile's
company directory before returning to this starter.

`AppMonthPicker` renders a controlled month/year selection with adaptive columns
and disabled-choice semantics. Supply localized month labels with `formatMonth`
and let the feature decide which dates are available. Keep the picker in an owned,
scrollable surface when text scaling can exceed the available height.

`AppDatePicker` renders a controlled seven-column month grid. The caller supplies
short localized weekday labels, full-date accessible descriptions, selection and
availability. Calendar navigation and date policy remain with the feature. Put it
inside a scrollable dialog so large text does not displace the fixed close action.
