# Owned action notifications

Eight raw notification files are copied exactly from validated Mobile
[`f3843601`](https://github.com/fajarxfce/hris-mobile/commit/f3843601fd8fb7af48a6d89171df0e42bc41f714).
The generic module provides payload/reference/input contracts, validation, bounded
opaque inventory, explicit Android PendingIntents and the native display adapter.
It starts no application notification lifetime and contains no HRIS authorization,
reply encryption, worker policy or credentials.

The caller owns one writer, supplies its namespace, icon, labels and explicit
Activity/private BroadcastReceiver components, and authenticates every callback.
Open uses an immutable Activity PendingIntent. Reply alone is mutable for Android
RemoteInput. Distinct canonical identities prevent hash collisions and old envelopes
from retargeting newer cards. Native submission does not prove visibility or reading.

The no-backup AtomicFile index retains at most 64 opaque references before notify.
An exact render fence protects replacements, including posts not yet visible in the
OS. Namespace cleanup preserves unrelated notifications. Corrupt inventory fails
closed while allowing best-effort cleanup of visible owned cards.

Android can retain inline reply history after cancel. Visible owned cards are
replaced with silent, empty, actionless updates using the public 600 ms OS expiry.
After confirmed neutral publication, the adapter allows600ms of inline-reply grace
and explicitly cancels the card, then confirms actual absence before dropping inventory.
The OS expiry remains a process-death fallback; foreground removal no longer depends
on its timely execution. Cancellation preserves inventory for a separate retry;
namespace retirement is batched. Mobile's actual API35 SystemUI proof separately
verifies that the original card, reply history and editor disappear. Other OEMs and
failure timing remain separate integration checks.

[Independent Starter evidence](measurements/action-notification-port-2026-11-03.json)
records the 260.027-second root/Android/lint/browser/production-Web pass: 377 desktop,
18 browser and six build-logic cases, zero failures/errors/skips. All ten new common
contract cases execute freshly on both desktop and browser. Lint has zero errors;
the generated APK is not installed. [Full hosted Apple](measurements/action-notification-apple-2026-11-03.json)
also passes:112 simulator cases/30 suites, including the ten new common contracts,
and a complete Xcode application build. All32 source hashes and the downloaded
artifact digest are verified.

The [explicit retirement port](measurements/notification-retirement-port-2026-11-07.json)
copies the one Android adapter from accepted Mobile11e2b36b. Mobile first reproduces
the delayed-expiry failure, then verifies explicit removal/namespace clear with the
neutral timeout postponed to60seconds, cancellation/recovery, ordinary process deaths
and actual SystemUI reply/card/editor disappearance. Three screenshots were inspected.
Starter independently passes root and Android build/lint in64.236s with unchanged
inputs, reused host XML, zero lint errors and44 existing warnings. Common/Apple sources
and APIs are unchanged, so no new Apple execution or HRIS callback policy is claimed.
