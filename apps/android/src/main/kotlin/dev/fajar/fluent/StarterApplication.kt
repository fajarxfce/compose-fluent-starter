package dev.fajar.fluent

import android.app.Application
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.app.navigation.AppLinkChannel
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.database.createInboxStore
import dev.fajar.starter.datastore.createUserPreferences

class StarterApplication : Application() {
    val links = AppLinkChannel()
    val environment = AppEnvironment.entries.single { it.id == BuildConfig.APP_ENVIRONMENT }
    val container by lazy {
        createAppContainer(
            createUserPreferences(this),
            createInboxStore(this),
            androidNotificationModule(this, environment),
            environment,
        )
    }
}
