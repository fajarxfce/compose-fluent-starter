@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package dev.fajar.starter.database

import kotlin.js.Promise
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

fun createInboxStore(namespace: String): InboxStore = IndexedDbInboxStore("$namespace.database")

private class IndexedDbInboxStore(name: String) : InboxStore {
    private val connection = lazy { openInboxDatabase(name) }
    private val database by connection
    private val changes =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply {
            tryEmit(Unit)
        }

    override fun observe() =
        changes.map {
            Json.decodeFromString<List<InboxRecord>>(
                readInbox(database.await<JsAny>()).await<JsString>().toString()
            )
        }

    override suspend fun upsert(record: InboxRecord) {
        writeInbox(database.await<JsAny>(), Json.encodeToString(record)).await<JsAny?>()
        changes.tryEmit(Unit)
    }

    override suspend fun markRead(id: String) {
        markInboxRead(database.await<JsAny>(), id).await<JsAny?>()
        changes.tryEmit(Unit)
    }

    override suspend fun clear() {
        clearInbox(database.await<JsAny>()).await<JsAny?>()
        changes.tryEmit(Unit)
    }

    override fun close() {
        if (connection.isInitialized()) closeInbox(database)
    }
}

@JsFun(
    """(name) => new Promise((resolve, reject) => {
    const request = indexedDB.open(name, 1);
    request.onupgradeneeded = () => request.result.createObjectStore('inbox', {keyPath: 'id'});
    request.onsuccess = () => { request.result.onversionchange = () => request.result.close(); resolve(request.result); };
    request.onerror = () => reject(request.error);
    request.onblocked = () => reject(new Error('Database upgrade is blocked.'));
})"""
)
private external fun openInboxDatabase(name: String): Promise<JsAny>

@JsFun(
    """(db) => new Promise((resolve, reject) => {
    const tx = db.transaction('inbox', 'readonly');
    const request = tx.objectStore('inbox').getAll();
    tx.oncomplete = () => resolve(JSON.stringify(request.result.sort((a,b) => b.createdAtEpochMillis - a.createdAtEpochMillis || a.id.localeCompare(b.id))));
    tx.onabort = () => reject(tx.error || new Error('Database read failed.'));
})"""
)
private external fun readInbox(database: JsAny): Promise<JsString>

@JsFun(
    """(db, json) => new Promise((resolve, reject) => {
    const tx = db.transaction('inbox', 'readwrite'); tx.objectStore('inbox').put(JSON.parse(json));
    tx.oncomplete = () => resolve(null); tx.onabort = () => reject(tx.error || new Error('Database write failed.'));
})"""
)
private external fun writeInbox(database: JsAny, json: String): Promise<JsAny?>

@JsFun(
    """(db, id) => new Promise((resolve, reject) => {
    const tx = db.transaction('inbox', 'readwrite'); const store = tx.objectStore('inbox');
    const request = store.get(id); request.onsuccess = () => { if (request.result) store.put({...request.result, read: true}); };
    tx.oncomplete = () => resolve(null); tx.onabort = () => reject(tx.error || new Error('Database update failed.'));
})"""
)
private external fun markInboxRead(database: JsAny, id: String): Promise<JsAny?>

@JsFun(
    """(db) => new Promise((resolve, reject) => {
    const tx = db.transaction('inbox', 'readwrite'); tx.objectStore('inbox').clear();
    tx.oncomplete = () => resolve(null); tx.onabort = () => reject(tx.error || new Error('Database clear failed.'));
})"""
)
private external fun clearInbox(database: JsAny): Promise<JsAny?>

@JsFun("(pending) => { pending.then(db => db.close(), () => {}); }")
private external fun closeInbox(database: Promise<JsAny>)
