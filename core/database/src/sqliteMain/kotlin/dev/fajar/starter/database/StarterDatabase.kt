package dev.fajar.starter.database

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.fajar.starter.database.dao.DashboardDao
import dev.fajar.starter.database.dao.InboxDao
import dev.fajar.starter.database.entities.*
import dev.fajar.starter.database.entities.InboxEntity

@Database(
    entities =
        [
            InboxEntity::class,
            DashboardEntity::class,
            ActivityEntity::class,
            ActivityPreferenceEntity::class,
            ActivityChangeEntity::class,
        ],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
)
@ConstructedBy(StarterDatabaseConstructor::class)
abstract class StarterDatabase : RoomDatabase() {
    abstract fun inboxDao(): InboxDao

    abstract fun dashboardDao(): DashboardDao
}

@Suppress("KotlinNoActualForExpect")
expect object StarterDatabaseConstructor : RoomDatabaseConstructor<StarterDatabase> {
    override fun initialize(): StarterDatabase
}
