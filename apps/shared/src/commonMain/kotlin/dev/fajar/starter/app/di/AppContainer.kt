package dev.fajar.starter.app.di

import dev.fajar.starter.auth.presentation.di.AuthPresentationModule
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.config.BuildEnvironment
import dev.fajar.starter.common.config.BuildRuntime
import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.dashboard.data.di.DashboardDataModule
import dev.fajar.starter.dashboard.presentation.di.DashboardPresentationModule
import dev.fajar.starter.database.AccountCacheStore
import dev.fajar.starter.database.AppDatabase
import dev.fajar.starter.database.DashboardStore
import dev.fajar.starter.database.InboxStore
import dev.fajar.starter.datastore.UserPreferencesStore
import dev.fajar.starter.demo.createDemoEngine
import dev.fajar.starter.featureflags.data.datasources.RemoteFeatureFlagSource
import dev.fajar.starter.featureflags.data.datasources.UnavailableFeatureFlagSource
import dev.fajar.starter.featureflags.data.di.FeatureFlagDataModule
import dev.fajar.starter.identity.data.di.IdentityModule
import dev.fajar.starter.identity.domain.usecases.AcquireSessionTokens
import dev.fajar.starter.network.*
import dev.fajar.starter.notifications.data.di.NotificationDataModule
import dev.fajar.starter.notifications.presentation.di.NotificationPresentationModule
import dev.fajar.starter.onboarding.data.di.OnboardingDataModule
import dev.fajar.starter.onboarding.presentation.di.OnboardingPresentationModule
import dev.fajar.starter.securestorage.*
import dev.fajar.starter.settings.data.di.SettingsDataModule
import dev.fajar.starter.settings.presentation.di.SettingsPresentationModule
import dev.fajar.starter.sync.data.datasources.WorkScheduler
import dev.fajar.starter.sync.data.di.SyncDataModule
import dev.fajar.starter.sync.domain.SyncTask
import dev.fajar.starter.worker.ForegroundWorkScheduler
import io.ktor.client.HttpClient
import io.ktor.http.Url
import org.koin.core.module.Module
import org.koin.core.qualifier.named
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
    workScheduler: WorkScheduler? = null,
    credentials: CredentialStore = MemoryCredentialStore(),
    remoteFeatureFlags: RemoteFeatureFlagSource = UnavailableFeatureFlagSource(),
) = koinApplication {
    modules(
        notificationPlatform,
        dev.fajar.starter.security.data.di.SecurityDataModule().module,
        SettingsDataModule().module,
        SettingsPresentationModule().module,
        SyncDataModule().module,
        FeatureFlagDataModule().module,
        NotificationDataModule().module,
        NotificationPresentationModule().module,
        module {
            single { environment }
            single<RemoteFeatureFlagSource> { remoteFeatureFlags }
            single<WorkScheduler> {
                    workScheduler
                        ?: ForegroundWorkScheduler(getAll<SyncTask>().map { it.key }.toSet())
                }
                .onClose { (it as? ForegroundWorkScheduler)?.close() }
            single<AppDatabase>(createdAtStart = true) { database }.onClose { it?.close() }
            single<CredentialStore> { credentials }.onClose { it?.close() }
            single<AccountCacheStore> { get<AppDatabase>().accounts }
            single<InboxStore> { get<AppDatabase>().inbox }
            single<DashboardStore> { get<AppDatabase>().dashboard }
            single<UserPreferencesStore> { preferences }.onClose { it?.close() }
            single<HttpClient>(named(HttpClients.Public)) {
                    createHttpClient(
                        if (BuildRuntime.demoBackend) createDemoEngine()
                        else createPlatformHttpEngine(),
                        HttpClientSettings(BuildRuntime.apiEndpoints.getValue(environment)),
                    )
                }
                .onClose { it?.close() }
            single<HttpClient>(named(HttpClients.Authenticated)) {
                    val acquireTokens = get<AcquireSessionTokens>()
                    val endpoint = BuildRuntime.apiEndpoints.getValue(environment)
                    createHttpClient(
                        if (BuildRuntime.demoBackend) createDemoEngine()
                        else createPlatformHttpEngine(),
                        HttpClientSettings(endpoint),
                    ) {
                        install(SessionAuthentication) {
                            origin = Url(endpoint)
                            acquire = { sessionId, rejected ->
                                when (val result = acquireTokens(sessionId, rejected)) {
                                    is AppResult.Failed -> result
                                    is AppResult.Success ->
                                        AppResult.Success(result.value?.accessToken)
                                }
                            }
                        }
                    }
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
