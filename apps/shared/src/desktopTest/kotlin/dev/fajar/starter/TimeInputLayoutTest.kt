package dev.fajar.starter

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import dev.fajar.starter.designsystem.components.AppTimeInput
import dev.fajar.starter.designsystem.theme.AppTheme
import kotlin.test.*
import org.junit.Rule
import org.junit.Test

class TimeInputLayoutTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun narrowLargeTextFieldsKeepIndependentControlledValuesAndDisabledTargets() {
        var hours by mutableStateOf("18")
        var minutes by mutableStateOf("30")
        var enabled by mutableStateOf(true)
        val changes = mutableListOf<Pair<String, String>>()
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, 2f)) {
                AppTheme {
                    Box(Modifier.width(320.dp).padding(16.dp).testTag("time-input")) {
                        AppTimeInput(
                            hours,
                            minutes,
                            "Jam (0–23)",
                            "Menit (0–59)",
                            {
                                hours = it
                                changes += "hours" to it
                            },
                            {
                                minutes = it
                                changes += "minutes" to it
                            },
                            enabled = enabled,
                        )
                    }
                }
            }
        }
        val frame = compose.onNodeWithTag("time-input").fetchSemanticsNode().boundsInRoot
        val hour = compose.onNodeWithContentDescription("Jam (0–23)")
        val minute = compose.onNodeWithContentDescription("Menit (0–59)")
        val hourBounds = hour.assertIsDisplayed().fetchSemanticsNode().boundsInRoot
        val minuteBounds = minute.assertIsDisplayed().fetchSemanticsNode().boundsInRoot
        assertTrue(hourBounds.left >= frame.left && minuteBounds.right <= frame.right)
        assertTrue(hourBounds.right < minuteBounds.left)
        hour.performTextReplacement("99")
        compose.runOnIdle {
            assertEquals("99", hours)
            assertEquals("30", minutes)
            assertEquals(listOf("hours" to "99"), changes)
        }
        minute.performTextReplacement("45")
        compose.runOnIdle {
            assertEquals(listOf("hours" to "99", "minutes" to "45"), changes)
            enabled = false
        }
        hour.assertIsDisplayed().assertIsNotEnabled()
        minute.assertIsDisplayed().assertIsNotEnabled()
    }
}
