package dev.fajar.starter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.fajar.starter.app.StarterApp
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.app.navigation.AppLinkChannel
import dev.fajar.starter.app.navigation.ResolveAppLink
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.database.createAppDatabase
import dev.fajar.starter.datastore.createUserPreferences
import dev.fajar.starter.notifications.data.datasources.*
import dev.fajar.starter.notifications.data.dto.*
import java.nio.file.Files
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

class AppLinkFlowTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun aColdLinkWaitsForLoginAndLaterLinksWaitUntilTheHostResumes() =
        exerciseLinkLifecycle("fluentstarter-dev://app/", null)

    @Test
    fun aConfiguredHttpsLinkKeepsOnboardingLoginAndForegroundOwnership() =
        exerciseLinkLifecycle("https://links.example.invalid/app/dev/", "links.example.invalid")

    private fun exerciseLinkLifecycle(prefix: String, httpsHost: String?) {
        val directory = Files.createTempDirectory("starter-links").toFile()
        val container =
            createAppContainer(
                createUserPreferences(directory),
                createAppDatabase(directory),
                module {
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
                },
            )
        container.koin.loadModules(
            listOf(module { factory { ResolveAppLink(AppEnvironment.Dev, httpsHost) } })
        )
        val links = AppLinkChannel()
        links.receive("${prefix}account")
        var hostState by mutableStateOf(Lifecycle.State.RESUMED)
        var visible by mutableStateOf(true)
        try {
            compose.setContent {
                val owner = remember {
                    object : LifecycleOwner {
                        override val lifecycle =
                            LifecycleRegistry(this).apply { currentState = Lifecycle.State.RESUMED }
                    }
                }
                val currentState = hostState
                SideEffect { owner.lifecycle.currentState = currentState }
                DisposableEffect(owner) {
                    onDispose { owner.lifecycle.currentState = Lifecycle.State.DESTROYED }
                }
                CompositionLocalProvider(LocalLifecycleOwner provides owner) {
                    if (visible)
                        Box(Modifier.size(420.dp, 760.dp)) { StarterApp(container, links.links) }
                }
            }
            waitFor("Your workspace")
            compose.onNodeWithText("Skip").performClick()
            waitFor("Use demo account")
            compose.onNodeWithText("Use demo account").performClick()
            compose.onNode(hasText("Sign in") and hasClickAction()).performScrollTo().performClick()
            waitFor("Alex Morgan")
            compose.onNode(hasText("Account") and isSelectable()).assertIsSelected()
            compose.runOnIdle { links.receive("${prefix}overview") }
            waitFor("Projects")
            if (httpsHost != null) {
                compose.runOnIdle { links.receive("https://other.example.invalid/app/dev/account") }
                compose.waitForIdle()
                compose.onNode(hasText("Overview") and isSelectable()).assertIsSelected()
            }
            compose.runOnIdle { hostState = Lifecycle.State.STARTED }
            compose.waitForIdle()
            compose.runOnIdle { links.receive("${prefix}activity") }
            compose.waitForIdle()
            compose.onNode(hasText("Overview") and isSelectable()).assertIsSelected()
            compose.runOnIdle { hostState = Lifecycle.State.RESUMED }
            waitFor("Workspace created")
            compose.onNode(hasText("Activity") and isSelectable()).assertIsSelected()
        } catch (failure: Throwable) {
            compose.mainClock.autoAdvance = false
            throw failure
        } finally {
            compose.runOnIdle { visible = false }
            compose.waitForIdle()
            links.close()
            container.close()
            directory.deleteRecursively()
        }
    }

    private fun waitFor(text: String) =
        compose.waitUntil(15_000) {
            compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
}
