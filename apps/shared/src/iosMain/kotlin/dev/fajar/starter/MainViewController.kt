package dev.fajar.starter

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.ComposeUIViewController
import dev.fajar.starter.app.StarterApp
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.datastore.createUserPreferences

fun MainViewController() =
    createAppContainer(createUserPreferences()).let { container ->
        ComposeUIViewController {
            DisposableEffect(container) { onDispose { container.close() } }
            StarterApp(container)
        }
    }
