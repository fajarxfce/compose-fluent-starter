@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package dev.fajar.starter.dashboard.domain

import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.common.result.*
import dev.fajar.starter.dashboard.domain.config.DashboardFlags
import dev.fajar.starter.dashboard.domain.entities.*
import dev.fajar.starter.dashboard.domain.repositories.DashboardRepository
import dev.fajar.starter.dashboard.domain.usecases.*
import dev.fajar.starter.featureflags.domain.entities.FlagSnapshot
import dev.fajar.starter.featureflags.domain.repositories.FeatureFlagRepository
import dev.fajar.starter.sync.domain.*
import dev.fajar.starter.sync.domain.repositories.SyncScheduleRepository
import kotlin.test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*

class SyncDashboardTest {
    @Test
    fun lateSuccessAfterCancellationNeverAcknowledgesTheOutbox() = runTest {
        val repository = SyncRepositoryFake()
        val pending = CompletableDeferred<Unit>()
        repository.onPush = {
            withContext(NonCancellable) { pending.await() }
            AppResult.Success(Unit)
        }
        val sync = SyncDashboard(repository)
        val job = launch { sync() }
        runCurrent()
        job.cancel()
        pending.complete(Unit)
        job.join()
        assertEquals(listOf("first"), repository.pending.map { it.operationId })
        assertEquals(0, repository.refreshes)
        repository.onPush = { AppResult.Success(Unit) }
        assertEquals(SyncResult.Complete, sync())
        assertEquals(listOf("first", "first"), repository.sent)
    }

    @Test
    fun failedAcknowledgementReplaysTheSameIdempotencyKey() = runTest {
        val repository = SyncRepositoryFake()
        repository.acknowledgement =
            AppResult.Failed(Failure(FailureKind.Storage, "Storage unavailable"))
        val sync = SyncDashboard(repository)
        assertIs<SyncResult.Blocked>(sync())
        repository.acknowledgement = AppResult.Success(Unit)
        assertEquals(SyncResult.Complete, sync())
        assertEquals(listOf("first", "first"), repository.sent)
    }

    @Test
    fun changesDuringUploadRemainQueuedAndConcurrentRunsSerialize() = runTest {
        val repository = SyncRepositoryFake()
        val uploaded = CompletableDeferred<Unit>()
        repository.onPush = {
            uploaded.await()
            AppResult.Success(Unit)
        }
        val sync = SyncDashboard(repository)
        val first = async { sync() }
        runCurrent()
        repository.pending += ActivityChange("second", "a", false)
        val second = async { sync() }
        runCurrent()
        assertEquals(listOf("first"), repository.sent)
        uploaded.complete(Unit)
        assertIs<SyncResult.Retry>(first.await())
        assertEquals(SyncResult.Complete, second.await())
        assertEquals(listOf("first", "second"), repository.sent)
        assertTrue(repository.pending.isEmpty())
    }

    @Test
    fun executionIsBoundedAndTransientFailuresKeepPendingData() = runTest {
        val repository = SyncRepositoryFake()
        repository.pending.clear()
        repeat(51) { repository.pending += ActivityChange("op-$it", "a", it % 2 == 0) }
        val sync = SyncDashboard(repository)
        assertIs<SyncResult.Retry>(sync())
        assertEquals(50, repository.sent.size)
        repository.onPush = { AppResult.Failed(Failure(FailureKind.Network, "Offline")) }
        assertIs<SyncResult.Retry>(sync())
        assertEquals(1, repository.pending.size)
        repository.onPush = { AppResult.Failed(Failure(FailureKind.Unauthorized, "Sign in")) }
        assertIs<SyncResult.Blocked>(sync())
        assertEquals(1, repository.pending.size)
    }

    @Test
    fun schedulingFailureDoesNotTurnALocalCommitIntoAFailedSave() = runTest {
        val repository = SyncRepositoryFake()
        var requested = 0
        val scheduler =
            object : SyncScheduleRepository {
                override suspend fun request(key: String): AppResult<Unit> {
                    requested++
                    return AppResult.Failed(
                        Failure(FailureKind.Unavailable, "Scheduler unavailable")
                    )
                }
            }
        val flags = FlagRepositoryFake()
        val save = SetActivitySaved(repository, scheduler, flags, AppEnvironment.Dev)
        assertIs<AppResult.Failed>(save("", true))
        assertEquals(0, requested)
        val result = assertIs<AppResult.Success<ActivitySaveResult>>(save("a", true))
        assertEquals(1, repository.saves)
        assertEquals(FailureKind.Unavailable, result.value.schedulingFailure?.kind)
        flags.current =
            AppResult.Success(FlagSnapshot(mapOf(DashboardFlags.SavedActivities.key to "false")))
        assertEquals(FailureKind.Unavailable, (save("a", false) as AppResult.Failed).failure.kind)
        assertEquals(1, repository.saves)
        assertEquals(1, requested)
        val unreadable = AppResult.Failed(Failure(FailureKind.Storage, "Unavailable"))
        flags.current = unreadable
        assertSame(unreadable, save("a", false))
        assertEquals(1, repository.saves)
        flags.current = AppResult.Success(FlagSnapshot())
        repository.localSave = AppResult.Failed(Failure(FailureKind.Storage, "Full"))
        assertIs<AppResult.Failed>(save("a", false))
        assertEquals(1, requested)
    }
}

private class SyncRepositoryFake : DashboardRepository {
    val pending = mutableListOf(ActivityChange("first", "a", true))
    val sent = mutableListOf<String>()
    var refreshes = 0
    var saves = 0
    var onPush: suspend () -> AppResult<Unit> = { AppResult.Success(Unit) }
    var acknowledgement: AppResult<Unit> = AppResult.Success(Unit)
    var localSave: AppResult<Unit> = AppResult.Success(Unit)

    override fun observe() = flowOf(AppResult.Success<Dashboard?>(null))

    override suspend fun pendingChanges(limit: Int) = AppResult.Success(pending.take(limit))

    override suspend fun push(change: ActivityChange): AppResult<Unit> {
        sent += change.operationId
        return onPush()
    }

    override suspend fun acknowledge(operationId: String): AppResult<Unit> {
        if (acknowledgement is AppResult.Success)
            pending.removeAll { it.operationId == operationId }
        return acknowledgement
    }

    override suspend fun refresh(): AppResult<Unit> {
        refreshes++
        return AppResult.Success(Unit)
    }

    override suspend fun setSaved(activityId: String, saved: Boolean): AppResult<Unit> {
        saves++
        return localSave
    }
}

private class FlagRepositoryFake : FeatureFlagRepository {
    var current: AppResult<FlagSnapshot> = AppResult.Success(FlagSnapshot())

    override fun observe() = flowOf(current)

    override suspend fun snapshot() = current

    override suspend fun refresh(fetchedAtEpochMillis: Long) = error("unused")

    override suspend fun setOverride(key: String, value: Boolean?) = error("unused")
}
