package dev.fajar.starter.identity.domain.repositories

import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.identity.domain.entities.AuthenticatedUser

interface IdentityRepository {
    suspend fun signIn(email: String, password: String): AppResult<AuthenticatedUser>

    suspend fun refresh(refreshToken: String): AppResult<AuthenticatedUser>
}
