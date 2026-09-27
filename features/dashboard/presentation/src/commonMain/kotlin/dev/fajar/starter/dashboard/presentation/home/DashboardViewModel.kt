package dev.fajar.starter.dashboard.presentation.home

import androidx.lifecycle.viewModelScope
import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.dashboard.domain.usecases.*
import dev.fajar.starter.identity.domain.usecases.ObserveUser
import dev.fajar.starter.identity.domain.usecases.SignOut
import dev.fajar.starter.presentation.mvi.MviViewModel
import dev.fajar.starter.sync.domain.SyncResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel(
    private val observeDashboard: ObserveDashboard,
    private val synchronize: SyncDashboard,
    private val setActivitySaved: SetActivitySaved,
    private val observeUser: ObserveUser,
    private val signOut: SignOut,
    @org.koin.core.annotation.InjectedParam initialTab: DashboardTab,
) :
    MviViewModel<DashboardState, DashboardEvent, DashboardEffect>(
        DashboardState(tab = initialTab)
    ) {
    private var refreshJob: Job? = null

    init {
        on<DashboardEvent.ActivitySavedChanged>(::onActivitySavedChanged)
        on<DashboardEvent.NotificationsRequested>(::onNotificationsRequested)
        on<DashboardEvent.TabSelected>(::onTabSelected)
        on<DashboardEvent.RefreshRequested>(::onRefreshRequested)
        on<DashboardEvent.SignOutRequested>(::onSignOutRequested)
        viewModelScope.launch {
            observeUser().collect { user -> updateState { it.copy(user = user) } }
        }
        viewModelScope.launch {
            observeDashboard().collect { result ->
                when (result) {
                    is AppResult.Success -> updateState { it.copy(dashboard = result.value) }
                    is AppResult.Failed ->
                        updateState { it.copy(error = result.failure.message, loading = false) }
                }
            }
        }
        onEvent(DashboardEvent.RefreshRequested)
    }

    private fun onTabSelected(event: DashboardEvent.TabSelected) {
        updateState { it.copy(tab = event.tab) }
    }

    private fun onRefreshRequested(event: DashboardEvent.RefreshRequested) {
        refreshJob?.cancel()
        updateState { it.copy(loading = true, error = null) }
        refreshJob =
            viewModelScope.launch {
                when (val result = synchronize()) {
                    SyncResult.Complete -> updateState { it.copy(loading = false, error = null) }
                    is SyncResult.Retry ->
                        updateState { it.copy(loading = false, error = result.failure?.message) }
                    is SyncResult.Blocked ->
                        updateState { it.copy(loading = false, error = result.failure.message) }
                }
            }
    }

    private fun onActivitySavedChanged(event: DashboardEvent.ActivitySavedChanged) {
        viewModelScope.launch {
            when (val result = setActivitySaved(event.id, event.saved)) {
                is AppResult.Failed -> updateState { it.copy(error = result.failure.message) }
                is AppResult.Success ->
                    updateState { it.copy(error = result.value.schedulingFailure?.message) }
            }
        }
    }

    private fun onSignOutRequested(event: DashboardEvent.SignOutRequested) {
        if (state.value.signingOut) return
        updateState { it.copy(signingOut = true, error = null) }
        viewModelScope.launch {
            when (val result = signOut()) {
                is AppResult.Success -> updateState { it.copy(signingOut = false) }
                is AppResult.Failed ->
                    updateState { it.copy(signingOut = false, error = result.failure.message) }
            }
        }
    }

    private fun onNotificationsRequested(event: DashboardEvent.NotificationsRequested) {
        viewModelScope.launch { emitEffect(DashboardEffect.OpenNotifications) }
    }
}
