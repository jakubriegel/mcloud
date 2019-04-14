package sandbox

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.client.MongoClients
import com.mongodb.ServerAddress
import com.mongodb.MongoClientSettings
import org.bson.Document

fun main(args: Array<String>) {

    val mongo = MongoClients.create(
            MongoClientSettings.builder()
                .applyToClusterSettings { builder -> builder.hosts(listOf(ServerAddress("localhost", 32768))) }
                .build()
    )

    val db = mongo.getDatabase("people")
    val personal = db.getCollection("personal")

    val mapper = ObjectMapper()
    val json: Map<String, Any> = mapper.readValue("{\"level\":59}")
    val filer = Document(json)
    val res = personal.find(filer).forEach { println("$it") }

}