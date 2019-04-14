package pl.poznan.put.cie.ts.mcloudcore.docker.response

data class ContainerCreatedDockerResponse (
        val id: String,
        val warnings: Any?
)