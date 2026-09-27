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

## Database

`core/database` exposes the raw `InboxStore` contract and `InboxRecord` DTO. Room
implements it on Android, iOS, and desktop using bundled SQLite. Web uses IndexedDB
because Room does not publish a Wasm target. Both adapters support ordered observation,
upsert, marking a record read, and clearing records. Repository owns domain mapping.

Room DAOs, entities, and database stay in `sqliteMain`; platform files only construct
the database. Commit exported JSON schemas. Increment the database version and add a
tested migration when changing an existing schema. No destructive fallback is enabled.
IndexedDB schema upgrades belong in `onupgradeneeded`; its transaction completion,
not individual request completion, determines write success. Observers update after
committed writes in the current app instance.

Run `./gradlew :core:datastore:desktopTest :core:database:desktopTest` for actual
filesystem/SQLite tests. Browser behavior also needs a browser smoke test.
