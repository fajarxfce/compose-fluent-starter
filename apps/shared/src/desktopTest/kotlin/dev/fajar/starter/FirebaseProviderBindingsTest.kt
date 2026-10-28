package dev.fajar.starter

import dev.fajar.starter.app.di.applicationFirebaseModule
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.database.createAppDatabase
import dev.fajar.starter.datastore.createUserPreferences
import dev.fajar.starter.identity.data.firebase.datasources.*
import dev.fajar.starter.identity.data.firebase.dto.FirebaseClientOptions
import dev.fajar.starter.identity.domain.firebase.entities.*
import dev.fajar.starter.identity.domain.firebase.repositories.FirebaseAuthenticationRepository
import dev.fajar.starter.identity.domain.repositories.SessionRepository
import dev.fajar.starter.notifications.data.datasources.*
import dev.fajar.starter.notifications.data.dto.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import java.nio.file.Files
import kotlin.test.*
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module

class FirebaseProviderBindingsTest {
    @Test
    fun installedProviderIsLazyOwnedAndDoesNotPublishAnApplicationSession() = runBlocking {
        val directory = Files.createTempDirectory("firebase-provider-bindings").toFile()
        var acquisitions = 0
        var calls = 0
        var closed = false
        val proof = "opaque-firebase-proof".repeat(5)
        val google =
            object : GoogleCredentialSource {
                override val supported = false

                override suspend fun open(webClientId: String): GoogleCredentialSession =
                    error("Unexpected Google interaction")

                override fun close() {
                    closed = true
                }
            }
        val options = FirebaseClientOptionsSource {
            FirebaseClientOptions("example-project", "A".repeat(39))
        }
        val container =
            createAppContainer(
                createUserPreferences(directory),
                createAppDatabase(directory),
                notifications(),
                firebaseOptions = options,
                googleCredentials = google,
            )
        try {
            container.koin.loadModules(
                listOf(
                    applicationFirebaseModule(options, google) {
                        acquisitions++
                        MockEngine { request ->
                            calls++
                            assertEquals("identitytoolkit.googleapis.com", request.url.host)
                            assertNull(request.headers[HttpHeaders.Authorization])
                            assertNull(request.headers[HttpHeaders.Cookie])
                            assertNull(request.headers["traceparent"])
                            respond(
                                """{"idToken":"$proof","refreshToken":"discard"}""",
                                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                            )
                        }
                    }
                )
            )
            val provider = container.koin.get<FirebaseAuthenticationRepository>()
            assertSame(provider, container.koin.get<FirebaseAuthenticationRepository>())
            assertEquals(
                setOf(FirebaseSignInProvider.EmailPassword),
                assertIs<AppResult.Success<FirebaseSignInConfiguration>>(provider.configuration())
                    .value
                    .providers,
            )
            assertEquals(0, acquisitions)
            assertEquals(
                proof,
                assertIs<AppResult.Success<FirebaseSignInProof>>(
                        provider.authenticate(
                            FirebaseSignInInput.EmailPassword(
                                "fixture@example.test",
                                "fixture-password",
                            )
                        )
                    )
                    .value
                    .idToken,
            )
            assertEquals(1, acquisitions)
            assertEquals(1, calls)
            assertEquals(AppResult.Success(null), container.koin.get<SessionRepository>().current())
        } finally {
            container.close()
            directory.deleteRecursively()
        }
        assertTrue(closed)
    }

    @Test
    fun defaultHostHasNoProviderAndDoesNotAcquireItsTransport() = runBlocking {
        val directory = Files.createTempDirectory("firebase-provider-disabled").toFile()
        val options = UnavailableFirebaseClientOptionsSource()
        val google = UnavailableGoogleCredentialSource()
        val container =
            createAppContainer(
                createUserPreferences(directory),
                createAppDatabase(directory),
                notifications(),
            )
        try {
            container.koin.loadModules(
                listOf(
                    applicationFirebaseModule(options, google) {
                        error("Disabled provider must not acquire HTTP")
                    }
                )
            )
            val provider = container.koin.get<FirebaseAuthenticationRepository>()
            assertEquals(
                AppResult.Success(FirebaseSignInConfiguration(false, null, emptySet())),
                provider.configuration(),
            )
            assertIs<AppResult.Failed>(provider.authenticate(FirebaseSignInInput.Google))
            assertEquals(AppResult.Success(null), container.koin.get<SessionRepository>().current())
        } finally {
            container.close()
            directory.deleteRecursively()
        }
    }

    private fun notifications() = module {
        single<NotificationPermissionSource> {
            object : NotificationPermissionSource {
                override suspend fun check() = NotificationPermission.Granted

                override suspend fun request() = NotificationPermission.Granted
            }
        }
        single<NotificationDisplaySource> {
            object : NotificationDisplaySource {
                override suspend fun show(payload: NotificationPayload) = Unit
            }
        }
        single<PushTokenSource> { UnavailablePushTokenSource() }
    }
}
