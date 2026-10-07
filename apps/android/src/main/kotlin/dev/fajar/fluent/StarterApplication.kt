package dev.fajar.fluent

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.app.navigation.AppLinkChannel
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.database.createAppDatabase
import dev.fajar.starter.datastore.createUserPreferences
import dev.fajar.starter.featureflags.data.datasources.AndroidFeatureFlagSource
import dev.fajar.starter.observability.*
import dev.fajar.starter.securestorage.AndroidCredentialStore
import dev.fajar.starter.sync.domain.SyncTask
import dev.fajar.starter.worker.AndroidWorkScheduler
import dev.fajar.starter.worker.SyncWorkerFactory

class StarterApplication : Application(), Configuration.Provider {
    val links = AppLinkChannel()
    val environment = AppEnvironment.entries.single { it.id == BuildConfig.APP_ENVIRONMENT }
    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(SyncWorkerFactory { container.koin.getAll<SyncTask>() })
                .build()

    private val scheduler by lazy { AndroidWorkScheduler(WorkManager.getInstance(this)) }
    val container by lazy {
        createAppContainer(
            createUserPreferences(this),
            createAppDatabase(this),
            androidNotificationModule(this, environment),
            environment,
            workScheduler = scheduler,
            credentials = AndroidCredentialStore(this),
            remoteFeatureFlags = AndroidFeatureFlagSource(this),
        )
    }

    override fun onCreate() {
        super.onCreate()
        Diagnostics.install(AndroidCrashSink(this, enabled = !BuildConfig.DEBUG))
        scheduler.installPeriodic(container.koin.getAll<SyncTask>().map { it.key }.toSet())
    }
}
