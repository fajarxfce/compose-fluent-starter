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
import dev.fajar.starter.identity.domain.entities.*
import dev.fajar.starter.identity.domain.repositories.SessionRepository
import dev.fajar.starter.identity.domain.usecases.ObserveUser
import dev.fajar.starter.identity.domain.usecases.SignOut
import dev.fajar.starter.security.domain.access.entities.*
import dev.fajar.starter.security.domain.access.repositories.AccessRepository
import dev.fajar.starter.security.domain.access.usecases.*
import dev.fajar.starter.sync.domain.repositories.SyncScheduleRepository
import kotlin.test.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun changingTabsRetainsReactiveContentWithoutRepeatingAcquisition() = runTest {
        val repository = RecoveringDashboard()
        val viewModel = model(repository)
        store.put("dashboard", viewModel)
        runCurrent()
        val loaded = Dashboard(9, 3, 6, emptyList())
        repository.cached.value = AppResult.Success(loaded)
        repository.responses.single().complete(AppResult.Success(Unit))
        runCurrent()
        val subscriptions = repository.subscriptions
        repeat(3) {
            for (tab in DashboardTab.entries) {
                viewModel.onEvent(DashboardEvent.TabSelected(tab))
                runCurrent()
                assertEquals(tab, viewModel.state.value.tab)
                assertEquals(loaded, viewModel.state.value.dashboard)
                assertFalse(viewModel.state.value.loading)
                assertEquals(1, repository.responses.size)
                assertEquals(subscriptions, repository.subscriptions)
            }
        }
        repository.cached.value = AppResult.Success(loaded.copy(projects = 10))
        runCurrent()
        viewModel.onEvent(DashboardEvent.BackRequested)
        runCurrent()
        assertEquals(DashboardTab.Overview, viewModel.state.value.tab)
        assertEquals(10, viewModel.state.value.dashboard?.projects)
        assertEquals(1, repository.responses.size)
        store.clear()
        runCurrent()
        assertEquals(0, repository.observers)
    }

    @Test
    fun aSuccessfulRefreshReopensAnUpstreamSubscriptionWithoutAutomaticRetries() = runTest {
        val repository = RecoveringDashboard()
        val viewModel = model(repository)
        store.put("dashboard", viewModel)
        runCurrent()
        assertEquals(FailureKind.Storage, viewModel.state.value.error?.kind)
        assertEquals(0, repository.observers)
        advanceTimeBy(500)
        runCurrent()
        assertEquals(1, repository.subscriptions)
        assertEquals(1, repository.responses.size)
        repository.cached.value = AppResult.Success(Dashboard(9, 3, 6, emptyList()))
        repository.responses.single().complete(AppResult.Success(Unit))
        runCurrent()
        assertEquals(9, viewModel.state.value.dashboard?.projects)
        assertEquals(1, repository.observers)
        assertNull(viewModel.state.value.error)
        store.clear()
        runCurrent()
        assertEquals(0, repository.observers)
        repository.cached.value = AppResult.Success(Dashboard(10, 3, 7, emptyList()))
        runCurrent()
        assertEquals(9, viewModel.state.value.dashboard?.projects)
    }

    @Test
    fun cancelledRefreshCannotPublishItsLateFailureOrRestartObservationAfterDisposal() = runTest {
        val repository = RecoveringDashboard()
        val viewModel = model(repository)
        store.put("dashboard", viewModel)
        runCurrent()
        repository.cached.value = AppResult.Success(Dashboard(9, 3, 6, emptyList()))
        repository.responses[0].complete(AppResult.Success(Unit))
        runCurrent()
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        assertEquals(3, repository.responses.size)
        assertTrue(viewModel.state.value.loading)
        assertNull(viewModel.state.value.error)
        repository.cached.value = AppResult.Success(Dashboard(10, 3, 7, emptyList()))
        repository.responses[2].complete(AppResult.Success(Unit))
        runCurrent()
        assertEquals(10, viewModel.state.value.dashboard?.projects)
        assertEquals(1, repository.observers)
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        val beforeDisposal = viewModel.state.value
        store.clear()
        runCurrent()
        assertEquals(beforeDisposal, viewModel.state.value)
        assertEquals(0, repository.observers)
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
                override fun observe(sessionId: String) = cached

                override suspend fun loadNextPage(sessionId: String) = AppResult.Success(Unit)

                override suspend fun refresh(sessionId: String): AppResult<Unit> {
                    val response = CompletableDeferred<AppResult<Unit>>()
                    pending += response
                    return response.await()
                }

                override suspend fun pendingChanges(sessionId: String, limit: Int) =
                    AppResult.Success(emptyList<ActivityChange>())

                override suspend fun push(sessionId: String, change: ActivityChange) =
                    error("unused")

                override suspend fun acknowledge(sessionId: String, operationId: String) =
                    error("unused")

                override suspend fun setSaved(
                    sessionId: String,
                    activityId: String,
                    saved: Boolean,
                ) = AppResult.Success(Unit)
            }
        val flagValues =
            MutableStateFlow<AppResult<FlagSnapshot>>(AppResult.Success(FlagSnapshot()))
        val viewModel = model(repository, flagValues)
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
        assertEquals("Offline", viewModel.state.value.error?.message)
        assertFalse(viewModel.state.value.loading)
        viewModel.onEvent(DashboardEvent.RefreshRequested)
        runCurrent()
        store.clear()
        flagValues.value = AppResult.Success(FlagSnapshot())
        pending[2].complete(AppResult.Success(Unit))
        runCurrent()
        assertNull(viewModel.state.value.error?.message)
        assertFalse(viewModel.state.value.savingAvailable)
    }

    private fun model(
        repository: DashboardRepository,
        flagValues: MutableStateFlow<AppResult<FlagSnapshot>> =
            MutableStateFlow(AppResult.Success(FlagSnapshot())),
    ): DashboardViewModel {
        val identity = TestSessions(testSession())
        val scheduler =
            object : SyncScheduleRepository {
                override suspend fun request(key: String) = AppResult.Success(Unit)
            }
        val flags =
            object : FeatureFlagRepository {
                override fun observe() = flagValues

                override suspend fun snapshot() = flagValues.value

                override suspend fun refresh(fetchedAtEpochMillis: Long) = error("unused")

                override suspend fun setOverride(key: String, value: Boolean?) = error("unused")
            }
        return DashboardViewModel(
            ObserveDashboard(repository, identity),
            SyncDashboard(repository, identity),
            LoadNextActivityPage(repository, identity),
            SetActivitySaved(
                repository,
                scheduler,
                flags,
                AppEnvironment.Dev,
                identity,
                GrantedAccess(),
            ),
            ObserveUser(identity),
            SignOut(identity),
            ObserveFeatureFlag(flags, AppEnvironment.Dev),
            ObservePermission(identity, GrantedAccess()),
            RefreshAccess(identity, GrantedAccess()),
            DashboardTab.Overview,
        )
    }
}

private class RecoveringDashboard : DashboardRepository {
    val cached = MutableStateFlow<AppResult<Dashboard?>>(AppResult.Success(null))
    val responses = mutableListOf<CompletableDeferred<AppResult<Unit>>>()
    var observers = 0
    var subscriptions = 0

    override fun observe(sessionId: String): Flow<AppResult<Dashboard?>> = flow {
        observers++
        subscriptions++
        try {
            if (subscriptions == 1)
                emit(AppResult.Failed(Failure(FailureKind.Storage, "Unavailable")))
            else emitAll(cached)
        } finally {
            observers--
        }
    }

    override suspend fun refresh(sessionId: String): AppResult<Unit> {
        val response = CompletableDeferred<AppResult<Unit>>()
        responses += response
        return try {
            response.await()
        } catch (_: CancellationException) {
            AppResult.Failed(Failure(FailureKind.Network, "Obsolete response"))
        }
    }

    override suspend fun pendingChanges(sessionId: String, limit: Int) =
        AppResult.Success(emptyList<ActivityChange>())

    override suspend fun loadNextPage(sessionId: String) = error("unused")

    override suspend fun push(sessionId: String, change: ActivityChange) = error("unused")

    override suspend fun acknowledge(sessionId: String, operationId: String) = error("unused")

    override suspend fun setSaved(sessionId: String, activityId: String, saved: Boolean) =
        error("unused")
}

private class TestSessions(initial: Session? = null) : SessionRepository {
    override val persistent = false
    val value = MutableStateFlow<AppResult<Session?>>(AppResult.Success(initial))

    override fun observe() = value

    override suspend fun current() = value.value

    override suspend fun clear(sessionId: String): AppResult<Boolean> {
        val session =
            when (val result = current()) {
                is AppResult.Failed -> return result
                is AppResult.Success -> result.value
            }
        return if (session?.id == sessionId) compareAndSet(session, null)
        else AppResult.Success(false)
    }

    override suspend fun compareAndSet(expected: Session?, updated: Session?): AppResult<Boolean> {
        val current = value.value
        if (current is AppResult.Failed) return current
        if ((current as AppResult.Success).value != expected) return AppResult.Success(false)
        value.value = AppResult.Success(updated)
        return AppResult.Success(true)
    }
}

private fun testSession() =
    Session(
        "session-a",
        User("1", "Alex", "demo@example.com"),
        SessionTokens("access", "refresh", Long.MAX_VALUE),
    )

private class GrantedAccess : AccessRepository {
    override fun observe(sessionId: String) = flowOf(AppResult.Success(grants(sessionId)))

    override suspend fun cached(sessionId: String) = AppResult.Success(grants(sessionId))

    override suspend fun refresh(sessionId: String) = AppResult.Success(Unit)

    override suspend fun invalidate(sessionId: String) = AppResult.Success(Unit)

    private fun grants(id: String) =
        AccessSnapshot(id, setOf("editor"), setOf(Permission.SaveActivity), Long.MAX_VALUE)
}
