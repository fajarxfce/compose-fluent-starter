package dev.fajar.starter.dashboard.data.datasources

import dev.fajar.starter.dashboard.data.api.DashboardApi
import org.koin.core.annotation.Single

@Single
class ApiDashboardRemoteDataSource(private val api: DashboardApi) : DashboardRemoteDataSource {
    override suspend fun load() = api.getDashboard()

    override suspend fun setSaved(
        operationId: String,
        request: dev.fajar.starter.dashboard.data.dto.ActivityPreferenceRequest,
    ) = api.putPreference(operationId, request)
}
