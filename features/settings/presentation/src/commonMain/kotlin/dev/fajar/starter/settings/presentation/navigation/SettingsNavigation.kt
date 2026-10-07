package dev.fajar.starter.settings.presentation.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.fajar.starter.localization.LocalAppLanguage
import dev.fajar.starter.settings.presentation.preferences.SettingsViewModel
import dev.fajar.starter.settings.presentation.preferences.pages.SettingsPage
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.settingsRoutes(onBack: () -> Unit) {
    composable<SettingsRoute> {
        val viewModel = koinViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        dev.fajar.starter.presentation.mvi.CollectEffects(viewModel.effects) { effect ->
            when (effect) {
                dev.fajar.starter.settings.presentation.preferences.SettingsEffect.Back -> onBack()
            }
        }
        SettingsPage(state, viewModel::onEvent)
    }
}

/** Composition entry point: observing a preference never changes the process or OS locale. */
@Composable
fun ProvideAppLanguage(content: @Composable () -> Unit) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    CompositionLocalProvider(LocalAppLanguage provides state.language, content = content)
}
