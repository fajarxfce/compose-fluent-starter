@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package dev.fajar.starter.dashboard.data

import dev.fajar.starter.common.result.*
import dev.fajar.starter.dashboard.data.datasources.DashboardRemoteDataSource
import dev.fajar.starter.dashboard.data.dto.*
import dev.fajar.starter.dashboard.data.repositories.LocalFirstDashboardRepository
import dev.fajar.starter.database.createAppDatabase
import java.nio.file.Files
import kotlin.test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*

class LocalFirstDashboardRepositoryTest {
    @Test
    fun localWritesNeedNoNetworkAndRefreshPreservesPreferences() = runTest {
        val directory = Files.createTempDirectory("dashboard-repository").toFile()
        val database = createAppDatabase(directory)
        val remote = RemoteSourceFake()
        val repository = LocalFirstDashboardRepository(remote, database.dashboard)
        try {
            assertIs<AppResult.Success<Unit>>(repository.refresh())
            remote.failure = java.io.IOException("private host detail")
            assertIs<AppResult.Success<Unit>>(repository.setSaved("a", true))
            assertEquals(1, remote.loads)
            val cached = assertIs<AppResult.Success<*>>(repository.observe().first()).value
            assertNotNull(cached)
            val failure = assertIs<AppResult.Failed>(repository.refresh()).failure
            assertEquals(FailureKind.Network, failure.kind)
            assertFalse(failure.message.contains("private host detail"))
            assertTrue(database.dashboard.observe().first()!!.activity.single().saved)
            assertEquals(1, database.dashboard.pendingChanges(5).size)
            assertIs<AppResult.Failed>(repository.setSaved("missing", true))
            remote.failure = null
            assertIs<AppResult.Success<Unit>>(repository.refresh())
            assertTrue(database.dashboard.observe().first()!!.activity.single().saved)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }

    @Test
    fun cancelledRemoteResultCannotReplaceTheCache() = runTest {
        val directory = Files.createTempDirectory("dashboard-cancel").toFile()
        val database = createAppDatabase(directory)
        val remote = RemoteSourceFake()
        val repository = LocalFirstDashboardRepository(remote, database.dashboard)
        try {
            repository.refresh()
            remote.pending = CompletableDeferred()
            remote.response = remote.response.copy(projects = 99)
            val refresh = launch { repository.refresh() }
            runCurrent()
            refresh.cancel()
            remote.pending!!.complete(Unit)
            refresh.join()
            assertEquals(2, database.dashboard.observe().first()!!.projects)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }

    @Test
    fun invalidRemoteIdentifiersAreRejectedBeforeReplacingValidContent() = runTest {
        val directory = Files.createTempDirectory("dashboard-validation").toFile()
        val database = createAppDatabase(directory)
        val remote = RemoteSourceFake()
        val repository = LocalFirstDashboardRepository(remote, database.dashboard)
        try {
            repository.refresh()
            remote.response =
                remote.response.copy(activity = remote.response.activity + remote.response.activity)
            assertIs<AppResult.Failed>(repository.refresh())
            assertEquals(1, database.dashboard.observe().first()!!.activity.size)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }
}

private class RemoteSourceFake : DashboardRemoteDataSource {
    var loads = 0
    var failure: Exception? = null
    var pending: CompletableDeferred<Unit>? = null
    var response = DashboardDto(2, 1, 3, listOf(ActivityDto("a", "Review", "Project", "09:00")))

    override suspend fun load(): DashboardDto {
        loads++
        failure?.let { throw it }
        withContext(NonCancellable) { pending?.await() }
        return response
    }

    override suspend fun setSaved(operationId: String, request: ActivityPreferenceRequest) = Unit
}
