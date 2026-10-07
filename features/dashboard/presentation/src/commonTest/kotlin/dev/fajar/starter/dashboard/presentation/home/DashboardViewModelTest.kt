@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package dev.fajar.starter.dashboard.presentation.home

import androidx.lifecycle.ViewModelStore
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.result.*
import dev.fajar.starter.dashboard.domain.config.DashboardFlags
import dev.fajar.starter.dashboard.domain.entities.*
import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository
import dev.fajar.starter.dashboard.domain.usecases.*
import dev.fajar.starter.featureflags.domain.entities.FlagSnapshot
import dev.fajar.starter.featureflags.domain.repositories.FeatureFlagRepository
import dev.fajar.starter.featureflags.domain.usecases.ObserveFeatureFlag
import dev.fajar.starter.identity.domain.entities.User
import dev.fajar.starter.identity.domain.repositories.IdentityRepository
import dev.fajar.starter.identity.domain.usecases.ObserveUser
import dev.fajar.starter.identity.domain.usecases.SignOut
import dev.fajar.starter.sync.domain.repositories.SyncScheduleRepository
import kotlin.test.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*

class DashboardViewModelTest {
    private val store = ViewModelStore()

    @BeforeTest
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun after() {
        store.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun cachedDataAppearsBeforeRefreshAndFailureDoesNotEraseIt() = runTest {
        val pending = mutableListOf<CompletableDeferred<AppResult<Unit>>>()
        val cached =
            MutableStateFlow<AppResult<Dashboard?>>(
                AppResult.Success(Dashboard(8, 3, 5, emptyList()))
            )
        val repository =
            object : DashboardRepository {
                override fun observe() = cached

                override suspend fun refresh(): AppResult<Unit> {
                    val response = CompletableDeferred<AppResult<Unit>>()
                    pending += response
                    return response.await()
                }

                override suspend fun pendingChanges(limit: Int) =
                    AppResult.Success(emptyList<ActivityChange>())

                override suspend fun push(change: ActivityChange) = error("unused")

                override suspend fun acknowledge(operationId: String) = error("unused")

                override suspend fun setSaved(activityId: String, saved: Boolean) =
                    AppResult.Success(Unit)
            }
        val identity =
            object : IdentityRepository {
                override fun observeUser() = flowOf<User?>(null)

                override suspend fun signOut() = AppResult.Success(Unit)

                override suspend fun signIn(email: String, password: String): AppResult<User> =
                    error("unused")
            }
        val scheduler =
            object : SyncScheduleRepository {
                override suspend fun request(key: String) = AppResult.Success(Unit)
            }
        val flagValues =
            MutableStateFlow<AppResult<FlagSnapshot>>(AppResult.Success(FlagSnapshot()))
        val flags =
            object : FeatureFlagRepository {
                override fun observe() = flagValues

                override suspend fun snapshot() = flagValues.value

                override suspend fun refresh(fetchedAtEpochMillis: Long) = error("unused")

                override suspend fun setOverride(key: String, value: Boolean?) = error("unused")
            }
        val viewModel =
            DashboardViewModel(
                ObserveDashboard(repository),
                SyncDashboard(repository),
                SetActivitySaved(repository, scheduler, flags, AppEnvironment.Dev),
                ObserveUser(identity),
                SignOut(identity),
                ObserveFeatureFlag(flags, AppEnvironment.Dev),
                DashboardTab.Overview,
            )
        store.put("dashboard", viewModel)
        runCurrent()
        assertEquals(8, viewModel.state.value.dashboard?.projects)
        assertTrue(viewModel.state.value.savingAvailable)
        flagValues.value =
            AppResult.Success(FlagSnapshot(mapOf(DashboardFlags.SavedActivities.key to "false")))
        runCurrent()
        assertFalse(viewModel.state.value.savingAvailable)
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        pending[0].complete(AppResult.Success(Unit))
        pending[1].complete(AppResult.Failed(Failure(FailureKind.Network, "Offline")))
        runCurrent()
        assertEquals(8, viewModel.state.value.dashboard?.projects)
        assertEquals("Offline", viewModel.state.value.error)
        assertFalse(viewModel.state.value.loading)
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        store.clear()
        flagValues.value = AppResult.Success(FlagSnapshot())
        pending[2].complete(AppResult.Success(Unit))
        runCurrent()
        assertNull(viewModel.state.value.error)
        assertFalse(viewModel.state.value.savingAvailable)
    }
}
