package dev.fajar.starter.identity.data.repositories

import dev.fajar.starter.identity.data.datasources.AuthRemoteDataSource
import dev.fajar.starter.identity.data.dto.*
import dev.fajar.starter.identity.data.mappers.toAuthenticatedUser
import dev.fajar.starter.identity.domain.repositories.IdentityRepository
import dev.fajar.starter.network.safeApiCall
import org.koin.core.annotation.Single

@Single
class DefaultIdentityRepository(private val remote: AuthRemoteDataSource) : IdentityRepository {
    override suspend fun signIn(email: String, password: String) = safeApiCall {
        remote.signIn(SignInRequest(email, password)).toAuthenticatedUser()
    }

    override suspend fun refresh(refreshToken: String) = safeApiCall {
        remote.refresh(RefreshRequest(refreshToken)).toAuthenticatedUser()
    }
}
