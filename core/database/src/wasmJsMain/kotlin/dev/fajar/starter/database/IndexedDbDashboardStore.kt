@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package dev.fajar.starter.database

import kotlin.js.Promise
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class IndexedDbDashboardStore(private val database: suspend () -> JsAny) : DashboardStore {
    private val changes =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply {
            tryEmit(Unit)
        }

    override fun observe() =
        changes.map {
            Json.decodeFromString<DashboardRecord?>(
                readDashboard(database()).await<JsString>().toString()
            )
        }

    override suspend fun replaceContent(record: DashboardRecord) {
        replaceDashboard(database(), Json.encodeToString(record)).await<JsAny?>()
        changes.tryEmit(Unit)
    }

    override suspend fun setSaved(change: ActivityChangeRecord): Boolean {
        val found =
            saveActivity(database(), Json.encodeToString(change)).await<JsBoolean>().toBoolean()
        changes.tryEmit(Unit)
        return found
    }

    override suspend fun pendingChanges(limit: Int) =
        Json.decodeFromString<List<ActivityChangeRecord>>(
            readChanges(database(), limit).await<JsString>().toString()
        )

    override suspend fun acknowledge(operationId: String) {
        acknowledgeChange(database(), operationId).await<JsAny?>()
        changes.tryEmit(Unit)
    }
}

@JsFun(
    """(db) => new Promise((resolve, reject) => {
    const tx = db.transaction(['dashboard', 'activity_preferences', 'dashboard_outbox'], 'readonly');
    const content = tx.objectStore('dashboard').get('current');
    const preferences = tx.objectStore('activity_preferences').getAll();
    const count = tx.objectStore('dashboard_outbox').count();
    tx.oncomplete = () => {
        if (!content.result) { resolve('null'); return; }
        const saved = new Map(preferences.result.map(row => [row.activityId, row.saved]));
        resolve(JSON.stringify({...content.result, pendingChanges: count.result,
            activity: content.result.activity.map(row => ({...row, saved: saved.get(row.id) || false}))}));
    };
    tx.onabort = () => reject(tx.error || new Error('Dashboard read failed.'));
})"""
)
private external fun readDashboard(database: JsAny): Promise<JsString>

@JsFun(
    """(db, json) => new Promise((resolve, reject) => {
    const tx = db.transaction('dashboard', 'readwrite');
    tx.objectStore('dashboard').put(JSON.parse(json), 'current');
    tx.oncomplete = () => resolve(null);
    tx.onabort = () => reject(tx.error || new Error('Dashboard write failed.'));
})"""
)
private external fun replaceDashboard(database: JsAny, json: String): Promise<JsAny?>

@JsFun(
    """(db, json) => new Promise((resolve, reject) => {
    const change = JSON.parse(json);
    const tx = db.transaction(['dashboard', 'activity_preferences', 'dashboard_outbox'], 'readwrite');
    const content = tx.objectStore('dashboard').get('current');
    const prefs = tx.objectStore('activity_preferences');
    const preference = prefs.get(change.activityId);
    let found = false;
    preference.onsuccess = () => {
        found = !!content.result?.activity.some(row => row.id === change.activityId);
        if (!found || (preference.result?.saved || false) === change.saved) return;
        prefs.put({activityId: change.activityId, saved: change.saved});
        tx.objectStore('dashboard_outbox').add(change);
    };
    tx.oncomplete = () => resolve(found);
    tx.onabort = () => reject(tx.error || new Error('Activity update failed.'));
})"""
)
private external fun saveActivity(database: JsAny, json: String): Promise<JsBoolean>

@JsFun(
    """(db, limit) => new Promise((resolve, reject) => {
    const tx = db.transaction('dashboard_outbox', 'readonly');
    const request = tx.objectStore('dashboard_outbox').getAll(undefined, limit);
    tx.oncomplete = () => resolve(JSON.stringify(request.result.map(({operationId, activityId, saved}) => ({operationId, activityId, saved}))));
    tx.onabort = () => reject(tx.error || new Error('Outbox read failed.'));
})"""
)
private external fun readChanges(database: JsAny, limit: Int): Promise<JsString>

@JsFun(
    """(db, id) => new Promise((resolve, reject) => {
    const tx = db.transaction('dashboard_outbox', 'readwrite');
    const store = tx.objectStore('dashboard_outbox');
    const request = store.index('operationId').getKey(id);
    request.onsuccess = () => { if (request.result !== undefined) store.delete(request.result); };
    tx.oncomplete = () => resolve(null);
    tx.onabort = () => reject(tx.error || new Error('Outbox acknowledgement failed.'));
})"""
)
private external fun acknowledgeChange(database: JsAny, operationId: String): Promise<JsAny?>
