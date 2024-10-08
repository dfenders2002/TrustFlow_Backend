package com.TrustFlow_Backend_Auth
import com.TrustFlow_Backend_Auth.models.UserRegisterResponse
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import java.util.UUID

class ApplicationTest {
    @Test
    fun testRegister() = testApplication {
        application {
            module()
        }
        val uniqueUsername = "testuser_${UUID.randomUUID()}"
        val uniqueEmail = "${uniqueUsername}@example.com"
        val response: HttpResponse = client.post("/register") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody("""{"username":"$uniqueUsername","password":"testpass","email":"$uniqueEmail"}""")
        }
        assertEquals(HttpStatusCode.OK, response.status)

        val responseBody = response.bodyAsText()
        val registerResponse = Json.decodeFromString<UserRegisterResponse>(responseBody)

        assertEquals("User registered", registerResponse.status)
        assertEquals(uniqueUsername, registerResponse.user?.username)
        assertEquals(uniqueEmail, registerResponse.user?.email)
    }


    @Test
    fun testLogin() = testApplication {
        application {
            module()
        }
        val response: HttpResponse = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"username":"testuser","password":"testpass","email":"test@example.com"}""")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.bodyAsText()
        assertTrue(responseBody.contains("Logged in"))
    }
}
