package dev.fajar.starter.dashboard.presentation.home

import androidx.lifecycle.viewModelScope
import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.dashboard.domain.config.DashboardFlags
import dev.fajar.starter.dashboard.domain.usecases.*
import dev.fajar.starter.featureflags.domain.usecases.ObserveFeatureFlag
import dev.fajar.starter.identity.domain.usecases.ObserveUser
import dev.fajar.starter.identity.domain.usecases.SignOut
import dev.fajar.starter.presentation.mvi.MviViewModel
import dev.fajar.starter.security.domain.access.entities.Permission
import dev.fajar.starter.security.domain.access.usecases.ObservePermission
import dev.fajar.starter.security.domain.access.usecases.RefreshAccess
import dev.fajar.starter.sync.domain.SyncResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel(
    private val observeDashboard: ObserveDashboard,
    private val synchronize: SyncDashboard,
    private val loadNextPage: LoadNextActivityPage,
    private val setActivitySaved: SetActivitySaved,
    private val observeUser: ObserveUser,
    private val signOut: SignOut,
    private val observeFeatureFlag: ObserveFeatureFlag,
    private val observePermission: ObservePermission,
    private val refreshAccess: RefreshAccess,
    @org.koin.core.annotation.InjectedParam initialTab: DashboardTab,
) :
    MviViewModel<DashboardState, DashboardEvent, DashboardEffect>(
        DashboardState(tab = initialTab)
    ) {
    private var refreshJob: Job? = null
    private var pageJob: Job? = null
    private var dashboardObservation: Job? = null

    init {
        on<DashboardEvent.ActivitySavedChanged>(::onActivitySavedChanged)
        on<DashboardEvent.FilesRequested>(::onFilesRequested)
        on<DashboardEvent.SettingsRequested>(::onSettingsRequested)
        on<DashboardEvent.NotificationsRequested>(::onNotificationsRequested)
        on<DashboardEvent.TabSelected>(::onTabSelected)
        on<DashboardEvent.BackRequested>(::onBackRequested)
        on<DashboardEvent.NextPageRequested>(::onNextPageRequested)
        on<DashboardEvent.RefreshRequested>(::onRefreshRequested)
        on<DashboardEvent.SignOutRequested>(::onSignOutRequested)
        viewModelScope.launch {
            observeUser().collect { result ->
                when (result) {
                    is AppResult.Success -> updateState { it.copy(user = result.value) }
                    is AppResult.Failed -> updateState { it.copy(error = result.failure) }
                }
            }
        }
        observeContent()
        viewModelScope.launch {
            observeFeatureFlag(DashboardFlags.SavedActivities).collect { result ->
                when (result) {
                    is AppResult.Success ->
                        updateState {
                            it.copy(savingAvailable = result.value.enabled, flagError = null)
                        }
                    is AppResult.Failed ->
                        updateState { it.copy(savingAvailable = false, flagError = result.failure) }
                }
            }
        }
        viewModelScope.launch {
            observePermission(Permission.SaveActivity).collect { result ->
                when (result) {
                    is AppResult.Success ->
                        updateState { it.copy(hasSavingPermission = result.value) }
                    is AppResult.Failed ->
                        updateState { it.copy(hasSavingPermission = false, error = result.failure) }
                }
            }
        }
        onEvent(DashboardEvent.RefreshRequested)
    }

    /** A completed upstream storage flow needs a new subscription after an admitted write. */
    private fun observeContent() {
        dashboardObservation?.cancel()
        dashboardObservation =
            viewModelScope.launch {
                observeDashboard().collect { result ->
                    ensureActive()
                    when (result) {
                        is AppResult.Success -> updateState { it.copy(dashboard = result.value) }
                        is AppResult.Failed ->
                            updateState { it.copy(error = result.failure, loading = false) }
                    }
                }
            }
    }

    private fun onTabSelected(event: DashboardEvent.TabSelected) {
        updateState { it.copy(tab = event.tab) }
    }

    private fun onBackRequested(event: DashboardEvent.BackRequested) {
        updateState { it.copy(tab = DashboardTab.Overview) }
    }

    private fun onRefreshRequested(event: DashboardEvent.RefreshRequested) {
        refreshJob?.cancel()
        pageJob?.cancel()
        updateState { it.copy(loading = true, loadingMore = false, pageError = null, error = null) }
        refreshJob =
            viewModelScope.launch {
                val permissions = refreshAccess()
                ensureActive()
                when (permissions) {
                    is AppResult.Failed ->
                        updateState { it.copy(accessError = permissions.failure) }
                    is AppResult.Success -> updateState { it.copy(accessError = null) }
                }
                val result = synchronize()
                ensureActive()
                when (result) {
                    SyncResult.Complete -> {
                        updateState { it.copy(loading = false, error = null) }
                        observeContent()
                    }
                    is SyncResult.Retry ->
                        updateState { it.copy(loading = false, error = result.failure) }
                    is SyncResult.Blocked ->
                        updateState { it.copy(loading = false, error = result.failure) }
                }
            }
    }

    private fun onNextPageRequested(event: DashboardEvent.NextPageRequested) {
        if (
            state.value.loading || state.value.loadingMore || state.value.dashboard?.hasMore != true
        )
            return
        updateState { it.copy(loadingMore = true, pageError = null) }
        pageJob =
            viewModelScope.launch {
                val result = loadNextPage()
                ensureActive()
                when (result) {
                    is AppResult.Success -> {
                        updateState { it.copy(loadingMore = false) }
                        observeContent()
                    }
                    is AppResult.Failed ->
                        updateState { it.copy(loadingMore = false, pageError = result.failure) }
                }
            }
    }

    private fun onActivitySavedChanged(event: DashboardEvent.ActivitySavedChanged) {
        val sessionId = state.value.dashboard?.sessionId ?: return
        viewModelScope.launch {
            val result = setActivitySaved(event.id, event.saved, sessionId)
            ensureActive()
            when (result) {
                is AppResult.Failed -> updateState { it.copy(error = result.failure) }
                is AppResult.Success -> {
                    updateState { it.copy(error = result.value.schedulingFailure) }
                    observeContent()
                }
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
                    updateState { it.copy(signingOut = false, error = result.failure) }
            }
        }
    }

    private fun onFilesRequested(event: DashboardEvent.FilesRequested) {
        viewModelScope.launch { emitEffect(DashboardEffect.OpenFiles) }
    }

    private fun onSettingsRequested(event: DashboardEvent.SettingsRequested) {
        viewModelScope.launch { emitEffect(DashboardEffect.OpenSettings) }
    }

    private fun onNotificationsRequested(event: DashboardEvent.NotificationsRequested) {
        viewModelScope.launch { emitEffect(DashboardEffect.OpenNotifications) }
    }
}
