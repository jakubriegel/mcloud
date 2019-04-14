package pl.poznan.put.cie.ts.mcloudcore.docker.response

import java.time.Instant

data class ContainerInspectDockerResponse (
        val id: String,
        val created: Instant,
        val state: State,
        val name: String,
        val networkSettings: NetworkSettings
)

data class State (
        val status: String,
        val startedAt: Instant,
        val finishedAt: Instant
)

data class NetworkSettings (
        val ports: Map<String, Array<Map<String, String>>>
) {
//        constructor(containerPort: Int, serverPort: Int) : this(
//                mapOf("$containerPort/tcp" to arrayOf(mapOf("HostPort" to "$serverPort")))
//        )
}