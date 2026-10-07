package dev.fajar.starter.network

import dev.fajar.starter.observability.*
import io.ktor.client.plugins.api.*
import kotlin.time.TimeSource

/** Measures transport requests without reading URLs, headers or payloads. */
val HttpDiagnostics =
    createClientPlugin("HttpDiagnostics") {
        on(Send) { request ->
            val started = TimeSource.Monotonic.markNow()
            val response = proceed(request)
            Diagnostics.record(
                Diagnostic(
                    DiagnosticArea.Network,
                    DiagnosticKind.HttpCompleted,
                    httpStatus = response.response.status.value,
                    durationMillis = started.elapsedNow().inWholeMilliseconds,
                )
            )
            response
        }
    }
