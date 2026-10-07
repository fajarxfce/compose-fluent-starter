package dev.fajar.starter.securestorage

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError

class AppleCredentialStore(private val client: AppleCredentialClient) : CredentialStore {
    override val persistent = true

    override suspend fun read(): String? = suspendCancellableCoroutine { continuation ->
        client.read { value, error ->
            if (continuation.isActive) {
                if (error != null) continuation.resumeWithException(AppleCredentialException(error))
                else continuation.resume(value)
            }
        }
    }

    override suspend fun write(value: String?): Unit = suspendCancellableCoroutine { continuation ->
        client.write(value) { error ->
            if (continuation.isActive) {
                if (error != null) continuation.resumeWithException(AppleCredentialException(error))
                else continuation.resume(Unit)
            }
        }
    }
}

class AppleCredentialException(val nativeError: NSError) : Exception("Keychain operation failed.")
