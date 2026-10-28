package dev.fajar.starter.app.navigation

import kotlin.test.*

class PendingAppLinkTest {
    @Test
    fun anIntentAcquiresOneSessionAndIsDiscardedOnLogoutOrReplacement() {
        val external = PendingAppLink(AppLink.Inbox)
        assertEquals(external, external.inSession(null))
        val owned = requireNotNull(external.inSession("session"))
        assertEquals(owned, owned.inSession("session"))
        assertNull(owned.inSession(null))
        assertNull(owned.inSession("replacement"))
    }
}
