package pl.poznan.put.cie.ts.mcloudcore.rest

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.cie.ts.mcloudcore.app.Mcloud
import pl.poznan.put.cie.ts.mcloudcore.config.PublicAPI

@PublicAPI
@RestController
@RequestMapping("mongo")
class MongoController (
        @Autowired
        val app: Mcloud
) {

    companion object {
        const val EMPTY_JSON_UTF_8 = "%7B%7D"
    }

    @ApiOperation(value = "Perform basic queries on selected MongoDB instance. Should be used only to peek the data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "results of query as list of objects", response = List::class)
    ])
    @GetMapping("filter")
    fun filter(
            @ApiParam("id of MongoDB to query", required = true)
            @RequestParam id: String,
            @ApiParam("name of database to query", required = true)
            @RequestParam db: String,
            @ApiParam("name of collection to query", required = true)
            @RequestParam collection: String,
            @ApiParam("query to perform in standard MongoDB JSON format serialized to UTF-8. Omit for whole database")
            @RequestParam(required = false, defaultValue = EMPTY_JSON_UTF_8) filter: String
    ): ResponseEntity<List<Map<String, Any>>> = ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(app.filterMongo(id, db, collection, filter))
}