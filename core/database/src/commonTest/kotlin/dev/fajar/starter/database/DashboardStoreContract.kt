package dev.fajar.starter.database

import kotlin.test.*
import kotlinx.coroutines.flow.first

val sampleDashboard =
    DashboardRecord(2, 1, 3, listOf(ActivityRecord("a", "Review", "Project", "09:00")), 10)

suspend fun verifyDashboardTransactions(database: AppDatabase) {
    val store = database.dashboard
    assertNull(store.observe().first())
    store.replaceContent(sampleDashboard)
    assertFalse(store.setSaved(ActivityChangeRecord("missing", "unknown", true)))
    assertTrue(store.pendingChanges(10).isEmpty())
    assertTrue(store.setSaved(ActivityChangeRecord("first", "a", true)))
    assertTrue(store.setSaved(ActivityChangeRecord("no-change", "a", true)))
    assertEquals(listOf("first"), store.pendingChanges(10).map { it.operationId })
    assertFails { store.setSaved(ActivityChangeRecord("first", "a", false)) }
    // Duplicate outbox identity fails the transaction, including its local preference write.
    assertTrue(store.observe().first()!!.activity.single().saved)
    assertTrue(store.setSaved(ActivityChangeRecord("second", "a", false)))
    store.acknowledge("first")
    store.acknowledge("first")
    assertEquals(listOf("second"), store.pendingChanges(10).map { it.operationId })
    store.replaceContent(sampleDashboard.copy(projects = 3))
    val refreshed = store.observe().first()!!
    assertEquals(3, refreshed.projects)
    assertFalse(refreshed.activity.single().saved)
    assertEquals(1, refreshed.pendingChanges)
    store.acknowledge("second")
    assertEquals(0, store.observe().first()!!.pendingChanges)
    store.setSaved(ActivityChangeRecord("third", "a", true))
    store.replaceContent(sampleDashboard)
    assertTrue(store.observe().first()!!.activity.single().saved)
}
