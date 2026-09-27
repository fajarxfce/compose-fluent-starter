package dev.fajar.starter.database

/** One platform owner per database file/namespace; stores do not own the connection. */
interface AppDatabase {
    val inbox: InboxStore
    val dashboard: DashboardStore

    fun close()
}
