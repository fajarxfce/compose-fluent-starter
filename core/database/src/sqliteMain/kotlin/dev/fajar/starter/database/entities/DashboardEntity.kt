package dev.fajar.starter.database.entities

import androidx.room.*

@Entity(tableName = "dashboard")
data class DashboardEntity(
    @PrimaryKey val id: Int = 1,
    val projects: Int,
    val active: Int,
    val members: Int,
    val updatedAtEpochMillis: Long,
)
