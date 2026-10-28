package dev.fajar.starter.app.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/** Wait for the admitted graph's destination to finish entering before replacing its stack. */
@OptIn(ExperimentalCoroutinesApi::class)
internal suspend fun NavHostController.awaitAppLinkNavigation(scope: String) {
    currentBackStackEntryFlow
        .flatMapLatest { entry -> entry.lifecycle.currentStateFlow.map { entry to it } }
        .first { (entry, state) ->
            state == Lifecycle.State.RESUMED &&
                entry.destination.hierarchy.any { it.route == "application/$scope" }
        }
}
