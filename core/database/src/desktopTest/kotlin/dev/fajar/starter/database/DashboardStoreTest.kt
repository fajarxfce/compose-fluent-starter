package dev.fajar.starter.database

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File
import java.nio.file.Files
import kotlin.test.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.*

class DashboardStoreTest {
    @Test
    fun atomicWritesOrderedOutboxAndStaleAcknowledgement() = runTest {
        val directory = Files.createTempDirectory("local-first-contract").toFile()
        val database = createAppDatabase(directory)
        try {
            verifyDashboardTransactions(database)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }

    @Test
    fun pendingChangesAndSavedPreferenceSurviveProcessRecreation() = runTest {
        val directory = Files.createTempDirectory("local-first-reopen").toFile()
        var database = createAppDatabase(directory)
        try {
            database.dashboard.replaceContent(sampleDashboard)
            database.dashboard.setSaved(ActivityChangeRecord("durable-id", "a", true))
            database.close()
            database = createAppDatabase(directory)
            assertTrue(database.dashboard.observe().first()!!.activity.single().saved)
            assertEquals("durable-id", database.dashboard.pendingChanges(1).single().operationId)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }

    @Test
    fun migrationFromVersionOnePreservesInbox() = runTest {
        val directory = Files.createTempDirectory("local-first-migration").toFile()
        val schema =
            Json.parseToJsonElement(
                    File("schemas/dev.fajar.starter.database.StarterDatabase/1.json").readText()
                )
                .jsonObject
                .getValue("database")
                .jsonObject
        BundledSQLiteDriver().open(directory.resolve("starter.db").absolutePath).use { connection ->
            val statements =
                schema.getValue("entities").jsonArray.map {
                    it.jsonObject
                        .getValue("createSql")
                        .jsonPrimitive
                        .content
                        .replace(
                            "\${TABLE_NAME}",
                            it.jsonObject.getValue("tableName").jsonPrimitive.content,
                        )
                } +
                    schema.getValue("setupQueries").jsonArray.map { it.jsonPrimitive.content } +
                    listOf(
                        "PRAGMA user_version = 1",
                        "INSERT INTO inbox VALUES ('existing', 'Keep me', 'Message', 'inbox', 1, 1)",
                    )
            statements.forEach { sql -> connection.prepare(sql).use { it.step() } }
        }
        val database = createAppDatabase(directory)
        try {
            assertEquals("Keep me", database.inbox.observe().first().single().title)
            assertTrue(database.inbox.observe().first().single().read)
            assertNull(database.dashboard.observe().first())
            database.dashboard.replaceContent(sampleDashboard)
            assertEquals(2, database.dashboard.observe().first()!!.projects)
        } finally {
            database.close()
            directory.deleteRecursively()
        }
    }
}
