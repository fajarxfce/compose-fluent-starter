package dev.fajar.starter.settings.presentation.preferences.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.fajar.starter.designsystem.components.*
import dev.fajar.starter.localization.*
import dev.fajar.starter.security.domain.access.entities.Permission
import dev.fajar.starter.settings.domain.entities.AppLanguage
import dev.fajar.starter.settings.presentation.preferences.*
import io.github.composefluent.FluentTheme
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.ArrowLeft
import io.github.composefluent.icons.regular.ArrowSync

@Composable
fun SettingsPage(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    securitySettings: @Composable () -> Unit = {},
) {
    AppPage(
        maxWidth = 640.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        topBar = {
            AppTopBar(
                appString(AppString.Settings),
                navigation = {
                    AppIconButton(
                        Icons.Regular.ArrowLeft,
                        appString(AppString.Back),
                        { onEvent(SettingsEvent.BackRequested) },
                    )
                },
            )
        },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AppHeading(appString(AppString.Language), style = FluentTheme.typography.bodyStrong)
            AppCard(Modifier.fillMaxWidth().selectableGroup(), padding = 4.dp) {
                AppLanguage.entries.forEach { language ->
                    key(language) {
                        AppRadioRow(
                            appString(
                                when (language) {
                                    AppLanguage.System -> AppString.SystemLanguage
                                    AppLanguage.English -> AppString.English
                                    AppLanguage.Indonesian -> AppString.Indonesian
                                }
                            ),
                            selected = state.language == language,
                            onClick = { onEvent(SettingsEvent.LanguageSelected(language)) },
                            enabled = !state.saving,
                        )
                    }
                }
            }
        }
        if (state.error != null) {
            AppFeedback(failureText(state.error))
            AppTextButton(appString(AppString.Retry), { onEvent(SettingsEvent.ReloadRequested) })
        }
        securitySettings()
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                AppHeading(
                    appString(AppString.AccountAccess),
                    Modifier.weight(1f),
                    style = FluentTheme.typography.bodyStrong,
                )
                AppIconButton(
                    Icons.Regular.ArrowSync,
                    appString(AppString.RefreshAccess),
                    { onEvent(SettingsEvent.AccessRefreshRequested) },
                    enabled = !state.loadingAccess,
                )
            }
            AppCard(Modifier.fillMaxWidth(), padding = 16.dp, spacing = 8.dp) {
                AppText(appString(AppString.AccountRoles, state.roles.joinToString(", ")))
                state.permissions.forEach { permission ->
                    AppText(
                        appString(
                            when (permission) {
                                Permission.SaveActivity -> AppString.PermissionSaveActivity
                                Permission.UploadFile -> AppString.PermissionUploadFile
                                Permission.DownloadFile -> AppString.PermissionDownloadFile
                            }
                        )
                    )
                }
                if (state.accessError != null) AppFeedback(failureText(state.accessError))
            }
        }
    }
}
