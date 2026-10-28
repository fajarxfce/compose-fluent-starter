package dev.fajar.starter.app.bootstrap

import dev.fajar.starter.app.navigation.PendingAppLink

data class AppState(val stage: AppStage = AppStage.Loading, val pendingLink: PendingAppLink? = null)
