package dev.fajar.starter.app.navigation

import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.config.AppLinkPath
import dev.fajar.starter.common.config.BuildAppLinks

/** Application navigation policy. External input can only select known destinations. */
class ResolveAppLink(
    environment: AppEnvironment,
    httpsHost: String? = BuildAppLinks.hosts[environment],
) {
    private val path = AppLinkPath(environment, httpsHost)

    operator fun invoke(uri: String): AppLink? {
        val admitted = path(uri) ?: return null
        return AppLink.entries.singleOrNull { it.path == admitted }
    }
}
