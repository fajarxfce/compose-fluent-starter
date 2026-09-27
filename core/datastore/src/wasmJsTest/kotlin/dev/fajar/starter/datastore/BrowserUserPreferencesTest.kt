@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package dev.fajar.starter.datastore

import androidx.datastore.core.CorruptionException
import dev.fajar.starter.datastore.proto.UserPreferences
import kotlin.test.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*

class BrowserUserPreferencesTest {
    private val namespace = "fluent-starter.browser-test"
    private val key = "$namespace.user_preferences.pb"

    @AfterTest
    fun cleanup() {
        localStorage.removeItem(key)
    }

    @Test
    fun updatesAreObservedAndPersistAcrossOwners() = runTest {
        val store = createUserPreferences(namespace)
        val values = mutableListOf<UserPreferences>()
        val observation =
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                store.data.toList(values)
            }
        assertFalse(values.last().onboarding_completed)
        store.update { it.copy(onboarding_completed = true) }
        runCurrent()
        assertTrue(values.last().onboarding_completed)
        store.close()
        runCurrent()
        assertTrue(observation.isCompleted)
        val reopened = createUserPreferences(namespace)
        try {
            assertTrue(reopened.data.first().onboarding_completed)
        } finally {
            reopened.close()
        }
    }

    @Test
    fun corruptionIsPreservedAndReported() = runTest {
        localStorage.setItem(key, "invalid-protobuf")
        val store = createUserPreferences(namespace)
        try {
            assertFailsWith<CorruptionException> { store.data.first() }
            assertEquals("invalid-protobuf", localStorage.getItem(key))
        } finally {
            store.close()
        }
    }

    @Test
    fun failedOrCancelledTransformsNeverPublishTheNewValue() = runTest {
        val store = createUserPreferences(namespace)
        try {
            store.data.first()
            val before = localStorage.getItem(key)
            assertFailsWith<IllegalStateException> { store.update { error("transform") } }
            assertEquals(before, localStorage.getItem(key))
            val cancelled = launch {
                store.update {
                    run {
                        cancel()
                        it.copy(onboarding_completed = true)
                    }
                }
            }
            cancelled.join()
            assertEquals(before, localStorage.getItem(key))
        } finally {
            store.close()
        }
    }
}
