# Local storage

## Proto DataStore

`core/datastore` owns `user_preferences.proto`, Wire generation, platform storage,
and `UserPreferencesStore`. `data` exposes `Flow<UserPreferences>`;
`update { current -> current.copy(...) }` is an atomic read-modify-write operation.
Keep transforms pure. A single application container owns each store and cancels
its scope on close. Do not create a store per screen.

Wire generates Kotlin models for all targets instead of JVM-only protobuf models.
Repositories consume the store, map DTOs, and wrap acquisition/mapping with
`safeStorageCall` or `safeStorageFlow`. Domain and presentation never import proto types.
Onboarding uses this path and migrates its previous platform preference once.

Android/iOS/desktop store protobuf files; Web stores the same protobuf bytes as
Base64 through a DataStore storage adapter. Browser ownership is one store per
key per page; this does not provide cross-tab reactive invalidation. Browser storage
is subject to browser eviction/private-mode restrictions. It is not credential storage.

Add fields with new protobuf numbers. Reserve deleted field numbers/names. Wire
preserves unknown fields through `copy`; do not reconstruct a message when updating
one field. Corrupt data is reported, not silently replaced with defaults.
