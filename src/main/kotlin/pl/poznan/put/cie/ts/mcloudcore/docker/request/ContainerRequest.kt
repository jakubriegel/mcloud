package pl.poznan.put.cie.ts.mcloudcore.docker.request

data class ContainerRequest (
        val image: String,
        val hostConfig: HostConfig = HostConfig(null)
) {
    constructor(image: String, containerPort: Int, serverPort: Int) : this(image, HostConfig(containerPort, serverPort))
}

data class HostConfig (
        val portBindings: Map<String, Array<Pair<String, String>>>?
) {
    constructor(containerPort: Int, serverPort: Int) : this(
            mapOf("$containerPort/tcp" to arrayOf("HostPort" to "$serverPort"))
    )
}