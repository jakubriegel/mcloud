package pl.poznan.put.cie.ts.mcloudcore.docker

import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.poznan.put.cie.ts.mcloudcore.httpclient.Http
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.response.readText
import io.ktor.http.HttpStatusCode
import org.jetbrains.annotations.TestOnly
import org.springframework.beans.factory.annotation.Autowired
import pl.poznan.put.cie.ts.mcloudcore.docker.request.ContainerRequest
import pl.poznan.put.cie.ts.mcloudcore.docker.response.ContainerCreatedDockerResponse
import pl.poznan.put.cie.ts.mcloudcore.docker.response.ContainerInspectDockerResponse

@Component
class DockerConnector (
        @Autowired
        val mapper: ObjectMapper,
        @Value("\${docker.host}")
        val host: String,
        @Value("\${docker.port}")
        val port: Int
) {

    private val http = Http(host, port)

    fun createContainer(name: String, request: ContainerRequest): String {
        val response = runBlocking {
            val body = mapper.writeValueAsString(request)
            http.post(
                    Http.path("containers/create", "name" to name),
                    body,
                    ContentType.Application.Json
            )
        }

        if (response.status == HttpStatusCode.Created) {
            val containerCreatedDockerResponse = mapper.readValue(
                    runBlocking { response.readText() },
                    ContainerCreatedDockerResponse::class.java
            )

            return containerCreatedDockerResponse.id
        }
        else TODO("handle errors [https://docs.docker.com/engine/api/v1.24/#create-a-container]")
    }

    fun startContainer(id: String): Boolean {
        val response = runBlocking { http.post("containers/$id/start") }
        return if (response.status == HttpStatusCode.NoContent) true
        else  TODO("handle starting errors")
    }

    fun inspectContainer(id: String): ContainerInspectDockerResponse {
        val rawJson = runBlocking {
            val response =http.get("containers/$id/json")
            response.readText()
        }
        return mapper.readValue(rawJson, ContainerInspectDockerResponse::class.java)
    }

    @TestOnly
    fun getAllContainers(): String {
        return runBlocking {
            http.get("containers/json?all=true").readText()
        }
    }

}