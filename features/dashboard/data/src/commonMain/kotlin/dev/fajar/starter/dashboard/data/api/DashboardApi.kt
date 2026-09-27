package dev.fajar.starter.dashboard.data.api

import dev.fajar.starter.dashboard.data.dto.ActivityPreferenceRequest
import dev.fajar.starter.dashboard.data.dto.DashboardDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.annotation.Single

@Single
class DashboardApi(private val client: HttpClient) {
    suspend fun getDashboard(): DashboardDto = client.get("dashboard").body()

    suspend fun putPreference(operationId: String, request: ActivityPreferenceRequest) {
        client.put("dashboard/preferences") {
            header("Idempotency-Key", operationId)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
