package dev.fajar.starter.notifications.presentation.inbox

import dev.fajar.starter.notifications.domain.entities.NotificationAccess
import dev.fajar.starter.notifications.domain.entities.NotificationMessage

data class NotificationState(
    val messages: List<NotificationMessage> = emptyList(),
    val loading: Boolean = true,
    val busy: Boolean = false,
    val access: NotificationAccess? = null,
    val status: String? = null,
    val error: String? = null,
)
