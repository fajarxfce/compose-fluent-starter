package dev.fajar.starter.dashboard.domain.repositories

import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.dashboard.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observe(): Flow<AppResult<Dashboard?>>

    suspend fun refresh(): AppResult<Unit>

    suspend fun setSaved(activityId: String, saved: Boolean): AppResult<Unit>

    suspend fun pendingChanges(limit: Int): AppResult<List<ActivityChange>>

    suspend fun push(change: ActivityChange): AppResult<Unit>

    suspend fun acknowledge(operationId: String): AppResult<Unit>
}
