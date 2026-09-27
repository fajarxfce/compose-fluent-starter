package dev.fajar.starter.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import dev.fajar.starter.designsystem.theme.AppColors
import io.github.composefluent.FluentTheme

@Composable
fun <T> AppBottomNavigation(
    items: List<AppNavigationItem<T>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxWidth().background(AppColors.surface),
        contentAlignment = Alignment.Center,
    ) {
        Row(Modifier.widthIn(max = 600.dp).fillMaxWidth().selectableGroup().padding(8.dp)) {
            items.forEach { item ->
                Column(
                    Modifier.weight(1f)
                        .background(
                            if (item.value == selected) AppColors.tint else AppColors.surface,
                            RoundedCornerShape(12.dp),
                        )
                        .selectable(
                            selected = item.value == selected,
                            role = Role.Tab,
                            onClick = { onSelected(item.value) },
                        )
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AppIcon(
                        item.icon,
                        tint = if (item.value == selected) AppColors.accent else AppColors.muted,
                    )
                    AppText(
                        item.label,
                        style = FluentTheme.typography.caption,
                        color = if (item.value == selected) AppColors.accent else AppColors.muted,
                    )
                }
            }
        }
    }
}
