package dev.fajar.starter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.fajar.starter.app.StarterApp
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.common.config.BuildEnvironment
import dev.fajar.starter.datastore.createUserPreferences
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val container =
        createAppContainer(createUserPreferences("fluent-starter.${BuildEnvironment.current.id}"))
    ComposeViewport(document.body!!) { StarterApp(container) }
}
