package dev.fajar.starter.database

import kotlinx.coroutines.flow.Flow

/** Raw storage operations. Local preference changes and outbox inserts commit together. */
interface DashboardStore {
    fun observe(): Flow<DashboardRecord?>

    /** Remote content never overwrites device-owned saved preferences. */
    suspend fun replaceContent(record: DashboardRecord)

    /** False means the activity does not exist; unchanged values do not enqueue another command. */
    suspend fun setSaved(change: ActivityChangeRecord): Boolean

    suspend fun pendingChanges(limit: Int): List<ActivityChangeRecord>

    /** Removes only this acknowledged operation, never a newer change for the same activity. */
    suspend fun acknowledge(operationId: String)
}
