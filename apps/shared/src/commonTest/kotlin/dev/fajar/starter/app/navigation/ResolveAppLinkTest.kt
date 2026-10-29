package dev.fajar.starter.app.navigation

import dev.fajar.starter.common.config.AppEnvironment
import kotlin.test.*

class ResolveAppLinkTest {
    private val resolve = ResolveAppLink(AppEnvironment.Dev)

    @Test
    fun acceptsOnlyKnownRoutesAndTheCurrentEnvironment() {
        assertEquals(AppLink.Inbox, resolve("fluentstarter-dev://app/inbox"))
        assertEquals(AppLink.Activity, resolve("#/activity"))
        assertEquals(AppLink.Account, resolve("FLUENTSTARTER-DEV://APP/account"))
        listOf(
                "fluentstarter://app/inbox",
                "fluentstarter-staging://app/inbox",
                "fluentstarter-dev://evil/inbox",
                "https://example.com/#/inbox",
                "fluentstarter-dev://app/account?redirect=evil",
                "#/../account",
                "#/%61ccount",
                "#/inbox/unknown",
                "#/" + "a".repeat(4096),
            )
            .forEach { assertNull(resolve(it), it) }
    }

    @Test
    fun configuredHttpsRetainsOnlyStarterDestinations() {
        val https = ResolveAppLink(AppEnvironment.Dev, "links.example.invalid")
        AppLink.entries.forEach {
            assertEquals(it, https("https://links.example.invalid/app/dev/${it.path}"))
        }
        listOf(
                "https://other.example.invalid/app/dev/account",
                "https://links.example.invalid/app/prod/account",
                "https://links.example.invalid/app/dev/pending-actions",
                "https://links.example.invalid/app/dev/data-sync",
                "https://links.example.invalid/app/dev/accounts/a/companies/b/inbox/c",
                "https://links.example.invalid/app/dev/account?read=true",
                "https://links.example.invalid/app/dev/Account",
            )
            .forEach { assertNull(https(it), it) }
    }
}
