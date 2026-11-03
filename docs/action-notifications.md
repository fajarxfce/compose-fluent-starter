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
The adapter confirms publication and native absence before dropping inventory;
namespace retirement is batched. Mobile's actual API35 SystemUI proof separately
verifies that the original card, reply history and editor disappear. Other OEMs and
failure timing remain separate integration checks.

[Independent Starter evidence](measurements/action-notification-port-2026-11-03.json)
records the 260.027-second root/Android/lint/browser/production-Web pass: 377 desktop,
18 browser and six build-logic cases, zero failures/errors/skips. All ten new common
contract cases execute freshly on both desktop and browser. Lint has zero errors;
the generated APK is not installed. Full hosted Apple is tracked separately.
