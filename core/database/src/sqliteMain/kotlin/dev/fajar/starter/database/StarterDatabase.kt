package dev.fajar.starter.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.fajar.starter.database.dao.InboxDao
import dev.fajar.starter.database.entities.InboxEntity

@Database(entities = [InboxEntity::class], version = 1, exportSchema = true)
@ConstructedBy(StarterDatabaseConstructor::class)
abstract class StarterDatabase : RoomDatabase() {
    abstract fun inboxDao(): InboxDao
}

@Suppress("KotlinNoActualForExpect")
expect object StarterDatabaseConstructor : RoomDatabaseConstructor<StarterDatabase> {
    override fun initialize(): StarterDatabase
}
