@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package dev.fajar.starter.app.bootstrap

import androidx.lifecycle.ViewModelStore
import dev.fajar.starter.app.navigation.*
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.identity.domain.entities.User
import dev.fajar.starter.identity.domain.repositories.IdentityRepository
import dev.fajar.starter.identity.domain.usecases.ObserveUser
import dev.fajar.starter.onboarding.domain.repositories.OnboardingRepository
import dev.fajar.starter.onboarding.domain.usecases.LoadOnboarding
import kotlin.test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*

class AppViewModelTest {
    @Test
    fun pendingLinkSurvivesOnboardingAndLoginAndIgnoresAnOlderAcknowledgement() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val owner = ViewModelStore()
        val user = MutableStateFlow<User?>(null)
        var complete = false
        val onboarding =
            object : OnboardingRepository {
                override suspend fun isComplete() = AppResult.Success(complete)

                override suspend fun complete() = AppResult.Success(Unit).also { complete = true }
            }
        val identity =
            object : IdentityRepository {
                override fun observeUser() = user

                override suspend fun signIn(email: String, password: String) = error("Not used")

                override suspend fun signOut() = AppResult.Success(Unit)
            }
        try {
            val vm =
                AppViewModel(
                    LoadOnboarding(onboarding),
                    ObserveUser(identity),
                    ResolveAppLink(AppEnvironment.Dev),
                )
            owner.put("app", vm)
            vm.onEvent(AppEvent.LinkReceived("fluentstarter-dev://app/inbox"))
            runCurrent()
            assertEquals(AppStage.Onboarding, vm.state.value.stage)
            assertEquals(AppLink.Inbox, vm.state.value.pendingLink)
            complete = true
            vm.onEvent(AppEvent.BootstrapRequested)
            runCurrent()
            assertEquals(AppStage.SignedOut, vm.state.value.stage)
            assertEquals(AppLink.Inbox, vm.state.value.pendingLink)
            user.value = User("1", "Alex", "alex@example.com")
            runCurrent()
            assertEquals(AppStage.SignedIn, vm.state.value.stage)
            vm.onEvent(AppEvent.LinkReceived("fluentstarter-dev://app/activity"))
            vm.onEvent(AppEvent.LinkHandled(AppLink.Inbox))
            runCurrent()
            assertEquals(AppLink.Activity, vm.state.value.pendingLink)
            vm.onEvent(AppEvent.LinkHandled(AppLink.Activity))
            runCurrent()
            assertNull(vm.state.value.pendingLink)
        } finally {
            owner.clear()
            Dispatchers.resetMain()
        }
    }
}
