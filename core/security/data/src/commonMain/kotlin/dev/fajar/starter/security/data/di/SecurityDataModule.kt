package dev.fajar.starter.security.data.di

import dev.fajar.starter.identity.domain.repositories.SessionRepository
import dev.fajar.starter.security.domain.access.repositories.AccessRepository
import dev.fajar.starter.security.domain.access.usecases.*
import org.koin.core.annotation.*

@Module
@ComponentScan("dev.fajar.starter.security.data")
class SecurityDataModule {
    @Factory
    fun permission(sessions: SessionRepository, access: AccessRepository) =
        ObservePermission(sessions, access)

    @Factory
    fun observeAccess(sessions: SessionRepository, access: AccessRepository) =
        ObserveAccess(sessions, access)

    @Single
    fun refreshAccess(sessions: SessionRepository, access: AccessRepository) =
        RefreshAccess(sessions, access)
}
