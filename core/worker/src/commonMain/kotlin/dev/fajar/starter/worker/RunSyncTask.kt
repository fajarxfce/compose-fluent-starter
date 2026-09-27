package dev.fajar.starter.worker

import dev.fajar.starter.common.result.*
import dev.fajar.starter.sync.domain.*
import kotlinx.coroutines.*

/** Last-resort runtime boundary for a faulty task; expected failures come from repositories. */
suspend fun runSyncTask(
    task: SyncTask,
    onException: (Exception) -> Unit = { println("Sync task: ${it::class.simpleName}") },
): SyncResult =
    try {
        currentCoroutineContext().ensureActive()
        val result = task()
        currentCoroutineContext().ensureActive()
        result
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (error: Exception) {
        currentCoroutineContext().ensureActive()
        onException(error)
        SyncResult.Blocked(Failure(FailureKind.Unexpected, "Sync could not be completed."))
    }
