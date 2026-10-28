package dev.fajar.starter.app.navigation

/** A process-local intent can wait for its first login but cannot move to another session. */
data class PendingAppLink(val target: AppLink, val sessionId: String? = null) {
    fun inSession(sessionId: String?): PendingAppLink? {
        if (sessionId == null) return takeIf { this.sessionId == null }
        if (this.sessionId != null && this.sessionId != sessionId) return null
        return copy(sessionId = sessionId)
    }

    override fun toString() = "PendingAppLink([redacted])"
}
