package dev.fajar.starter.observability

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.*

/** Closed fields deliberately exclude free text, URLs, bodies, headers and user identifiers. */
enum class DiagnosticArea {
    Network,
    Storage,
    Notification,
    Worker,
    FeatureFlags,
    Application,
}

enum class DiagnosticKind {
    OperationFailed,
    HttpCompleted,
}

data class Diagnostic(
    val area: DiagnosticArea,
    val kind: DiagnosticKind,
    val category: String? = null,
    val httpStatus: Int? = null,
    val durationMillis: Long? = null,
)

fun interface DiagnosticSink {
    fun record(event: Diagnostic, error: Exception?)
}

/** Process-owned logging. Only the platform host installs a sink, before opening containers. */
object Diagnostics {
    private val sink = MutableStateFlow<DiagnosticSink>(ConsoleDiagnosticSink)

    fun install(value: DiagnosticSink) {
        sink.value = value
    }

    fun failure(area: DiagnosticArea, error: Exception) {
        record(Diagnostic(area, DiagnosticKind.OperationFailed, error::class.simpleName), error)
    }

    fun record(event: Diagnostic, error: Exception? = null) {
        try {
            sink.value.record(event, error)
        } catch (_: Exception) {
            ConsoleDiagnosticSink.record(event, null)
        }
    }
}

object ConsoleDiagnosticSink : DiagnosticSink {
    override fun record(event: Diagnostic, error: Exception?) {
        println(encodeDiagnostic(event))
    }
}

/** Exception messages, causes and toString() never participate in encoding. */
fun encodeDiagnostic(event: Diagnostic): String =
    buildJsonObject {
            put("area", event.area.name)
            put("event", event.kind.name)
            event.category
                ?.takeIf { it.matches(Regex("[A-Za-z][A-Za-z0-9_]{0,79}")) }
                ?.let { put("category", it) }
            event.httpStatus?.takeIf { it in 100..599 }?.let { put("http_status", it) }
            event.durationMillis?.takeIf { it >= 0 }?.let { put("duration_ms", it) }
        }
        .toString()
