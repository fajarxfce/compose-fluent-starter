package dev.fajar.starter.worker

import dev.fajar.starter.sync.domain.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.*

/** Application-host entry point. Closing/cancelling the host stops pending work and retries. */
class ForegroundSyncWorker(
    private val tasks: List<SyncTask>,
    private val scheduler: ForegroundWorkScheduler,
    private val interval: Duration = 15.minutes,
    private val retryDelay: Duration = 30.seconds,
) {
    init {
        require(tasks.map { it.key }.distinct().size == tasks.size) { "Duplicate sync task keys." }
        require(interval.isPositive() && retryDelay.isPositive())
    }

    fun start(scope: CoroutineScope): Job =
        scope.launch {
            supervisorScope {
                tasks.forEach { task ->
                    launch {
                        val requests = scheduler.requests(task.key)
                        var wait: Duration? = Duration.ZERO
                        var backoff = retryDelay
                        while (isActive) {
                            if (wait == null) requests.receive()
                            else withTimeoutOrNull(wait) { requests.receive() }
                            when (runSyncTask(task)) {
                                SyncResult.Complete -> {
                                    wait = interval
                                    backoff = retryDelay
                                }
                                is SyncResult.Retry -> {
                                    wait = backoff
                                    backoff = (backoff * 2).coerceAtMost(interval)
                                }
                                is SyncResult.Blocked -> {
                                    wait = null
                                    backoff = retryDelay
                                }
                            }
                        }
                    }
                }
            }
        }
}
