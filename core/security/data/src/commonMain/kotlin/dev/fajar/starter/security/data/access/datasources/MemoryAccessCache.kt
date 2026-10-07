package dev.fajar.starter.security.data.access.datasources

import dev.fajar.starter.security.data.access.dto.AccessResponse
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Single

/** Process-local, independently keyed records; a late response cannot replace another session. */
@Single
class MemoryAccessCache : AccessCache {
    private val records = MutableStateFlow<Map<String, AccessResponse>>(emptyMap())

    override fun observe(sessionId: String) = records.map { it[sessionId] }.distinctUntilChanged()

    override fun read(sessionId: String) = records.value[sessionId]

    override fun remove(sessionId: String) {
        records.update { it - sessionId }
    }

    override fun write(sessionId: String, response: AccessResponse) {
        records.update { it + (sessionId to response) }
    }
}
