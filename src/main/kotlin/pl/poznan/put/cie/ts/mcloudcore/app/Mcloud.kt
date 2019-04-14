package pl.poznan.put.cie.ts.mcloudcore.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClients
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.util.UriUtils
import pl.poznan.put.cie.ts.mcloudcore.docker.request.ContainerRequest
import pl.poznan.put.cie.ts.mcloudcore.docker.DockerConnector
import pl.poznan.put.cie.ts.mcloudcore.rest.response.ContainerCreatedResponse
import java.nio.charset.StandardCharsets

@Component
class Mcloud (
        @Autowired
        val connector: DockerConnector,
        @Autowired
        val mapper: ObjectMapper
) {

    companion object {
        private const val MONGO_IMAGE = "mongo:3.4.10-jessie"
        private const val MONGO_PORT = 27017
        private const val RANDOM_FREE_PORT = 0
    }

    fun createMongoInstance(name: String): ContainerCreatedResponse {
        val containerRequest = ContainerRequest(MONGO_IMAGE, MONGO_PORT, RANDOM_FREE_PORT)
        val id = connector.createContainer(name, containerRequest)

        return ContainerCreatedResponse(id)
    }

    fun startMongoInstance(id: String): MongoInstance {
        connector.startContainer(id)
        return inspectMongoInstance(id)
    }

    fun inspectMongoInstance(id: String): MongoInstance {
        val container = connector.inspectContainer(id)
        return MongoInstance(
                container.name,
                container.id,
                container.created,
                container.state.status,
                container.networkSettings.ports.getValue("$MONGO_PORT/tcp")[0].getValue("HostPort").toInt()
        )
    }

    fun filterMongo(id: String, dbName: String, collection: String, filter: String): List<Map<String, Any>> {
        val filterStr = UriUtils.decode(filter, StandardCharsets.UTF_8)
        val filterJson: Map<String, Any> = mapper.readValue(filterStr)
        return filterMongo(id, dbName, collection, filterJson)
    }

    fun filterMongo(id: String, dbName: String, collection: String, filter: Map<String, Any>): List<Map<String, Any>> {
        val port = inspectMongoInstance(id).port

        val mongo = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings { builder -> builder.hosts(listOf(ServerAddress(connector.host, port))) }
                        .build()
        )

        val db = mongo.getDatabase(dbName)
        val personal = db.getCollection(collection)

        val filer = Document(filter)
        val result = mutableListOf<Map<String, Any>>()
        personal.find(filer).forEach { result.add(it.toMap()) }
        return result
    }
}