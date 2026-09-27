package dev.fajar.starter.dashboard.domain.usecases

import dev.fajar.starter.common.result.*
import dev.fajar.starter.dashboard.domain.entities.ActivitySaveResult
import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository
import dev.fajar.starter.sync.domain.repositories.SyncScheduleRepository

class SetActivitySaved(
    private val repository: DashboardRepository,
    private val scheduler: SyncScheduleRepository,
) {
    suspend operator fun invoke(id: String, saved: Boolean): AppResult<ActivitySaveResult> {
        if (id.isBlank())
            return AppResult.Failed(Failure(FailureKind.Validation, "Select an activity."))
        when (val local = repository.setSaved(id, saved)) {
            is AppResult.Failed -> return local
            is AppResult.Success -> Unit
        }
        val request = scheduler.request(SyncDashboard.KEY)
        return AppResult.Success(ActivitySaveResult((request as? AppResult.Failed)?.failure))
    }
}
