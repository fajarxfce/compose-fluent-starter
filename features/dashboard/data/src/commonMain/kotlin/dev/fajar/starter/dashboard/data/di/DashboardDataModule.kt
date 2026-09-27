package dev.fajar.starter.dashboard.data.di

import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository
import dev.fajar.starter.dashboard.domain.usecases.*
import dev.fajar.starter.sync.domain.SyncTask
import dev.fajar.starter.sync.domain.repositories.SyncScheduleRepository
import org.koin.core.annotation.*

@Module
@ComponentScan("dev.fajar.starter.dashboard.data")
class DashboardDataModule {
    @Factory fun observe(repository: DashboardRepository) = ObserveDashboard(repository)

    @Factory fun request(scheduler: SyncScheduleRepository) = RequestDashboardSync(scheduler)

    @Factory
    fun save(repository: DashboardRepository, scheduler: SyncScheduleRepository) =
        SetActivitySaved(repository, scheduler)

    @Single(binds = [SyncTask::class, SyncDashboard::class])
    fun sync(repository: DashboardRepository) = SyncDashboard(repository)
}
