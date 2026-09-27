package dev.fajar.starter.dashboard.data.datasources

import dev.fajar.starter.dashboard.data.dto.DashboardDto

interface DashboardRemoteDataSource {
    suspend fun load(): DashboardDto

    suspend fun setSaved(
        operationId: String,
        request: dev.fajar.starter.dashboard.data.dto.ActivityPreferenceRequest,
    )
}
