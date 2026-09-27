package dev.fajar.starter.app.di

import dev.fajar.starter.auth.presentation.di.AuthPresentationModule
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.config.BuildEnvironment
import dev.fajar.starter.dashboard.data.di.DashboardDataModule
import dev.fajar.starter.dashboard.presentation.di.DashboardPresentationModule
import dev.fajar.starter.database.AppDatabase
import dev.fajar.starter.database.DashboardStore
import dev.fajar.starter.database.InboxStore
import dev.fajar.starter.datastore.UserPreferencesStore
import dev.fajar.starter.demo.createDemoEngine
import dev.fajar.starter.identity.data.di.IdentityModule
import dev.fajar.starter.network.createHttpClient
import dev.fajar.starter.notifications.data.di.NotificationDataModule
import dev.fajar.starter.notifications.presentation.di.NotificationPresentationModule
import dev.fajar.starter.onboarding.data.di.OnboardingDataModule
import dev.fajar.starter.onboarding.presentation.di.OnboardingPresentationModule
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.dsl.onClose
import org.koin.ksp.generated.module

/** Platform entry points own this isolated container and its lifetime. */
fun createAppContainer(
    preferences: UserPreferencesStore,
    database: AppDatabase,
    notificationPlatform: Module,
    environment: AppEnvironment = BuildEnvironment.current,
) = koinApplication {
    modules(
        notificationPlatform,
        NotificationDataModule().module,
        NotificationPresentationModule().module,
        module {
            single { environment }
            single<AppDatabase>(createdAtStart = true) { database }.onClose { it?.close() }
            single<InboxStore> { get<AppDatabase>().inbox }
            single<DashboardStore> { get<AppDatabase>().dashboard }
            single<UserPreferencesStore> { preferences }.onClose { it?.close() }
            single<HttpClient> {
                    createHttpClient(createDemoEngine(), "https://demo.fluent.local/")
                }
                .onClose { it?.close() }
        },
        IdentityModule().module,
        OnboardingDataModule().module,
        OnboardingPresentationModule().module,
        AuthPresentationModule().module,
        DashboardDataModule().module,
        DashboardPresentationModule().module,
        ApplicationModule().module,
    )
}
