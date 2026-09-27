package dev.fajar.starter.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import io.github.composefluent.Colors
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.generateShades

@OptIn(ExperimentalFluentApi::class)
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val dark = isSystemInDarkTheme()
    val colors = remember(dark) { Colors(generateShades(Color(0xFF0078D4)), darkMode = dark) }
    FluentTheme(colors = colors, compactMode = false, useAcrylicPopup = false, content = content)
}
