package pl.poznan.put.cie.ts.mcloudcore.httpclient

import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.call
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.util.cio.toByteArray

class Http (
        private val host: String,
        private val port: Int
) {
    private val client = HttpClient()

    suspend fun get(
            path: String
    ) = request(path, HttpMethod.Get)

    suspend fun post(
            path: String,
            body: String = "",
            contentType: ContentType = ContentType.Application.Json
    ) = request(path, HttpMethod.Post, body, contentType)

    private suspend fun request(
            path: String,
            method: HttpMethod,
            body: String = "",
            contentType: ContentType = ContentType.Any
    ): HttpResponse {
        return client.request {
            url("http://$host:$port/$path")
            this.method = method
            this.body = TextContent(body, contentType)
        }
    }

    fun close() = client.close()

    companion object {
        fun path(path: String, vararg params: Pair<String, Any>)
                = "$path?${params.joinToString { "${it.first}=${it.second}&" }}"
    }
}