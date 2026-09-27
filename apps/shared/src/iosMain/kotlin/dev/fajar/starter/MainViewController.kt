package dev.fajar.starter

import androidx.compose.ui.window.ComposeUIViewController
import dev.fajar.starter.app.StarterApp
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.app.navigation.AppLinkChannel
import dev.fajar.starter.common.config.BuildEnvironment
import dev.fajar.starter.database.createAppDatabase
import dev.fajar.starter.datastore.createUserPreferences
import dev.fajar.starter.notifications.data.datasources.*
import dev.fajar.starter.notifications.domain.entities.NotificationMessage
import dev.fajar.starter.notifications.domain.usecases.ReceiveNotification
import kotlin.time.Clock
import kotlinx.coroutines.*
import org.koin.dsl.module

/** Swift's application delegate owns the container, callback scope, and incoming intents. */
class AppleAppHost(firebase: AppleFirebaseClient) {
    private val links = AppLinkChannel()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val push = ApplePushTokenSource(firebase)
    private val container =
        createAppContainer(
            createUserPreferences(),
            createAppDatabase(),
            module {
                single<NotificationPermissionSource> { AppleNotificationPermissionSource() }
                single<NotificationDisplaySource> { AppleNotificationDisplaySource() }
                single<PushTokenSource> { push }
            },
        )

    fun viewController() = ComposeUIViewController { StarterApp(container, links.links) }

    fun openLink(uri: String) {
        links.receive(uri)
    }

    fun openNotification(destination: String) {
        links.receive("${BuildEnvironment.current.linkScheme}://app/$destination")
    }

    fun receivedToken(token: String) {
        push.receivedToken(token)
    }

    fun receivedNotification(
        id: String,
        title: String,
        body: String,
        destination: String,
        completion: () -> Unit,
    ) {
        scope.launch {
            try {
                container.koin.get<ReceiveNotification>()(
                    NotificationMessage(
                        id,
                        title,
                        body,
                        destination,
                        Clock.System.now().toEpochMilliseconds(),
                    ),
                    systemDisplayed = true,
                )
            } finally {
                completion()
            }
        }
    }

    fun close() {
        scope.cancel()
        links.close()
        container.close()
    }
}
