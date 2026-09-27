@file:OptIn(kotlin.uuid.ExperimentalUuidApi::class)

package dev.fajar.starter.dashboard.data.repositories

import dev.fajar.starter.common.result.*
import dev.fajar.starter.dashboard.data.datasources.DashboardRemoteDataSource
import dev.fajar.starter.dashboard.data.dto.ActivityPreferenceRequest
import dev.fajar.starter.dashboard.data.mappers.*
import dev.fajar.starter.dashboard.domain.entities.ActivityChange
import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository
import dev.fajar.starter.database.*
import dev.fajar.starter.network.safeApiCall
import dev.fajar.starter.storage.*
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalFirstDashboardRepository(
    private val remote: DashboardRemoteDataSource,
    private val local: DashboardStore,
) : DashboardRepository {
    override fun observe() = safeStorageFlow(local.observe().map { it?.toDashboard() })

    override suspend fun refresh(): AppResult<Unit> =
        when (
            val response = safeApiCall {
                remote.load().toRecord(Clock.System.now().toEpochMilliseconds())
            }
        ) {
            is AppResult.Failed -> response
            is AppResult.Success -> safeStorageCall { local.replaceContent(response.value) }
        }

    override suspend fun setSaved(activityId: String, saved: Boolean): AppResult<Unit> =
        when (
            val stored = safeStorageCall {
                local.setSaved(ActivityChangeRecord(Uuid.random().toString(), activityId, saved))
            }
        ) {
            is AppResult.Failed -> stored
            is AppResult.Success ->
                if (stored.value) AppResult.Success(Unit)
                else
                    AppResult.Failed(
                        Failure(FailureKind.Validation, "This activity is no longer available.")
                    )
        }

    override suspend fun pendingChanges(limit: Int) = safeStorageCall {
        local.pendingChanges(limit).map { ActivityChange(it.operationId, it.activityId, it.saved) }
    }

    override suspend fun push(change: ActivityChange) = safeApiCall {
        remote.setSaved(
            change.operationId,
            ActivityPreferenceRequest(change.activityId, change.saved),
        )
    }

    override suspend fun acknowledge(operationId: String) = safeStorageCall {
        local.acknowledge(operationId)
    }
}
