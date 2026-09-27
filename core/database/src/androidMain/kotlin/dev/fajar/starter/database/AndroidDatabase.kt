package dev.fajar.starter.database

import android.content.Context
import androidx.room.Room

fun createInboxStore(context: Context): InboxStore =
    RoomInboxStore(Room.databaseBuilder<StarterDatabase>(context.applicationContext, "starter.db"))
