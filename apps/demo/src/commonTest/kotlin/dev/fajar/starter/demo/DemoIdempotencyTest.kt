package dev.fajar.starter.demo

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.test.*
import kotlinx.coroutines.test.runTest

class DemoIdempotencyTest {
    @Test
    fun duplicateDeliveryIsAcceptedAndReusingAKeyForAnotherPayloadIsRejected() = runTest {
        val client = HttpClient(createDemoEngine(latencyMillis = 0))
        try {
            suspend fun send(key: String?, saved: Boolean) =
                client
                    .put("https://demo.fluent.local/dashboard/preferences") {
                        if (key != null) header("Idempotency-Key", key)
                        bearerAuth("demo:demo-user:${Long.MAX_VALUE}")
                        contentType(ContentType.Application.Json)
                        setBody("""{"activityId":"a","saved":$saved}""")
                    }
                    .status
            assertEquals(HttpStatusCode.BadRequest, send(null, true))
            assertEquals(HttpStatusCode.NoContent, send("one", true))
            assertEquals(HttpStatusCode.NoContent, send("one", true))
            assertEquals(HttpStatusCode.Conflict, send("one", false))
            assertEquals(HttpStatusCode.NoContent, send("two", false))
        } finally {
            client.close()
        }
    }
}
