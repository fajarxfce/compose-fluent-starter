package dev.fajar.starter.database.dao

import androidx.room.*
import dev.fajar.starter.database.dto.DashboardRow
import dev.fajar.starter.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DashboardDao {
    @Query(
        """
        SELECT d.projects, d.active, d.members, d.updatedAtEpochMillis,
            (SELECT COUNT(*) FROM dashboard_outbox) AS pendingChanges,
            a.id AS activityId, a.title, a.detail, a.time, COALESCE(p.saved, 0) AS saved
        FROM dashboard d LEFT JOIN dashboard_activity a ON 1 = 1
        LEFT JOIN activity_preferences p ON p.activityId = a.id
        WHERE d.id = 1 ORDER BY a.position
    """
    )
    abstract fun observe(): Flow<List<DashboardRow>>

    @Upsert protected abstract suspend fun upsertDashboard(row: DashboardEntity)

    @Insert protected abstract suspend fun insertActivity(rows: List<ActivityEntity>)

    @Query("DELETE FROM dashboard_activity") protected abstract suspend fun clearActivity()

    @Query("SELECT EXISTS(SELECT 1 FROM dashboard_activity WHERE id = :id)")
    protected abstract suspend fun containsActivity(id: String): Boolean

    @Query("SELECT saved FROM activity_preferences WHERE activityId = :id")
    protected abstract suspend fun saved(id: String): Boolean?

    @Upsert protected abstract suspend fun upsertPreference(row: ActivityPreferenceEntity)

    @Insert protected abstract suspend fun insertChange(row: ActivityChangeEntity)

    @Transaction
    open suspend fun replaceContent(dashboard: DashboardEntity, activity: List<ActivityEntity>) {
        upsertDashboard(dashboard)
        clearActivity()
        insertActivity(activity)
    }

    @Transaction
    open suspend fun setSaved(change: ActivityChangeEntity): Boolean {
        if (!containsActivity(change.activityId)) return false
        if ((saved(change.activityId) ?: false) == change.saved) return true
        upsertPreference(ActivityPreferenceEntity(change.activityId, change.saved))
        insertChange(change)
        return true
    }

    @Query("SELECT * FROM dashboard_outbox ORDER BY sequence LIMIT :limit")
    abstract suspend fun pendingChanges(limit: Int): List<ActivityChangeEntity>

    @Query("DELETE FROM dashboard_outbox WHERE operationId = :operationId")
    abstract suspend fun acknowledge(operationId: String)
}
