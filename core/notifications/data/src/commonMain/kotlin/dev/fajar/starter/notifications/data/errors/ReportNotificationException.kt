package dev.fajar.starter.notifications.data.errors

/** Replace at a safe boundary with an internal diagnostic sink; never log payloads or tokens. */
fun reportNotificationException(error: Exception) {
    println("Notification operation failed: ${error::class.simpleName}")
}
