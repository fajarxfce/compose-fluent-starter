package dev.fajar.starter.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.fajar.starter.database.entities.InboxEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map

class RoomInboxStore(builder: RoomDatabase.Builder<StarterDatabase>) : InboxStore {
    private val database =
        builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO).build()
    private val dao = database.inboxDao()

    override fun observe() =
        dao.observe().map { rows ->
            rows.map {
                InboxRecord(
                    it.id,
                    it.title,
                    it.body,
                    it.destination,
                    it.createdAtEpochMillis,
                    it.read,
                )
            }
        }

    override suspend fun upsert(record: InboxRecord) {
        dao.upsert(
            InboxEntity(
                record.id,
                record.title,
                record.body,
                record.destination,
                record.createdAtEpochMillis,
                record.read,
            )
        )
    }

    override suspend fun markRead(id: String) = dao.markRead(id)

    override suspend fun clear() = dao.clear()

    override fun close() = database.close()
}
