package dev.fajar.starter.database

import androidx.room.Room
import java.io.File

fun createInboxStore(directory: File): InboxStore {
    check(directory.isDirectory || directory.mkdirs()) { "Database directory is unavailable." }
    return RoomInboxStore(
        Room.databaseBuilder<StarterDatabase>(directory.resolve("starter.db").absolutePath)
    )
}
