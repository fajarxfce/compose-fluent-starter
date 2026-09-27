package dev.fajar.starter.sync.data.errors

import dev.fajar.starter.common.result.*
import kotlinx.coroutines.*

suspend fun <T> safeWorkCall(
    onException: (Exception) -> Unit = { println("Work scheduler: ${it::class.simpleName}") },
    operation: suspend () -> T,
): AppResult<T> =
    try {
        currentCoroutineContext().ensureActive()
        val value = operation()
        currentCoroutineContext().ensureActive()
        AppResult.Success(value)
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (error: Exception) {
        currentCoroutineContext().ensureActive()
        onException(error)
        AppResult.Failed(
            Failure(FailureKind.Unavailable, "Sync could not be scheduled. Try again.")
        )
    }
