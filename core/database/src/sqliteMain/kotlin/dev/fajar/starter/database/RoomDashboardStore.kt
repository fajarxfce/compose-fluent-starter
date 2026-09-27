package dev.fajar.starter.database

import dev.fajar.starter.database.dao.DashboardDao
import dev.fajar.starter.database.entities.*
import kotlinx.coroutines.flow.map

internal class RoomDashboardStore(private val dao: DashboardDao) : DashboardStore {
    override fun observe() =
        dao.observe().map { rows ->
            rows.firstOrNull()?.let { first ->
                DashboardRecord(
                    first.projects,
                    first.active,
                    first.members,
                    rows.mapNotNull { row ->
                        row.activityId?.let { id ->
                            ActivityRecord(
                                id,
                                requireNotNull(row.title),
                                requireNotNull(row.detail),
                                requireNotNull(row.time),
                                row.saved,
                            )
                        }
                    },
                    first.updatedAtEpochMillis,
                    first.pendingChanges,
                )
            }
        }

    override suspend fun replaceContent(record: DashboardRecord) =
        dao.replaceContent(
            DashboardEntity(
                projects = record.projects,
                active = record.active,
                members = record.members,
                updatedAtEpochMillis = record.updatedAtEpochMillis,
            ),
            record.activity.mapIndexed { index, row ->
                ActivityEntity(row.id, row.title, row.detail, row.time, index)
            },
        )

    override suspend fun setSaved(change: ActivityChangeRecord) =
        dao.setSaved(
            ActivityChangeEntity(
                operationId = change.operationId,
                activityId = change.activityId,
                saved = change.saved,
            )
        )

    override suspend fun pendingChanges(limit: Int) =
        dao.pendingChanges(limit).map {
            ActivityChangeRecord(it.operationId, it.activityId, it.saved)
        }

    override suspend fun acknowledge(operationId: String) = dao.acknowledge(operationId)
}
