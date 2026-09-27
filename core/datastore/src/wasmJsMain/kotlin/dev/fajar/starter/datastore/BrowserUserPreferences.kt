package dev.fajar.starter.datastore

import androidx.datastore.core.ReadScope
import androidx.datastore.core.Storage
import androidx.datastore.core.StorageConnection
import androidx.datastore.core.WriteScope
import androidx.datastore.core.createSingleProcessCoordinator
import dev.fajar.starter.datastore.proto.UserPreferences
import kotlinx.browser.localStorage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okio.Buffer
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.toByteString

fun createUserPreferences(namespace: String): UserPreferencesStore =
    ProtoUserPreferencesStore(
        BrowserProtoStorage("$namespace.user_preferences.pb"),
        migrations =
            listOf(
                LegacyOnboardingMigration {
                    namespace == "fluent-starter.prod" &&
                        localStorage.getItem("fluent-starter.onboarding.complete") == "true"
                }
            ),
    )

/** DataStore 1.2's storage adapter; one owning DataStore per key and browser page. */
private class BrowserProtoStorage(private val key: String) : Storage<UserPreferences> {
    override fun createConnection(): StorageConnection<UserPreferences> =
        BrowserProtoConnection(key)
}

private class BrowserProtoConnection(private val key: String) : StorageConnection<UserPreferences> {
    override val coordinator = createSingleProcessCoordinator(key)
    private val mutex = Mutex()
    private var closed = false

    override suspend fun <R> readScope(
        block: suspend ReadScope<UserPreferences>.(Boolean) -> R
    ): R =
        mutex.withLock {
            check(!closed)
            block(BrowserProtoScope(key), true)
        }

    override suspend fun writeScope(block: suspend WriteScope<UserPreferences>.() -> Unit) =
        mutex.withLock {
            check(!closed)
            block(BrowserProtoScope(key))
        }

    override fun close() {
        closed = true
    }
}

private class BrowserProtoScope(private val key: String) : WriteScope<UserPreferences> {
    override suspend fun readData(): UserPreferences {
        val encoded = localStorage.getItem(key) ?: return UserPreferences()
        val bytes =
            encoded.decodeBase64()
                ?: throw androidx.datastore.core.CorruptionException("Invalid preference encoding.")
        return UserPreferencesSerializer.readFrom(Buffer().write(bytes))
    }

    override suspend fun writeData(value: UserPreferences) {
        localStorage.setItem(key, UserPreferences.ADAPTER.encode(value).toByteString().base64())
    }

    override fun close() = Unit
}
