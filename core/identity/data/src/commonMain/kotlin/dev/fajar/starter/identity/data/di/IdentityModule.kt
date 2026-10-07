package dev.fajar.starter.identity.data.di

import dev.fajar.starter.identity.domain.repositories.*
import dev.fajar.starter.identity.domain.usecases.*
import org.koin.core.annotation.*

@Module
@ComponentScan("dev.fajar.starter.identity.data")
class IdentityModule {
    @Factory fun validation() = ValidateSignIn()

    @Factory
    fun signIn(identity: IdentityRepository, sessions: SessionRepository) =
        SignIn(identity, sessions)

    @Factory fun signOut(sessions: SessionRepository) = SignOut(sessions)

    @Factory fun observeUser(sessions: SessionRepository) = ObserveUser(sessions)

    @Factory fun restore(sessions: SessionRepository) = RestoreSession(sessions)

    @Single
    fun tokens(identity: IdentityRepository, sessions: SessionRepository) =
        AcquireSessionTokens(identity, sessions)
}
