package dev.fajar.starter.dashboard.domain.usecases

import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository

class ObserveDashboard(private val repository: DashboardRepository) {
    operator fun invoke() = repository.observe()
}
