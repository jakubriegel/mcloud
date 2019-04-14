package pl.poznan.put.cie.ts.mcloudcore.rest

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.cie.ts.mcloudcore.app.Mcloud
import pl.poznan.put.cie.ts.mcloudcore.config.PublicAPI
import pl.poznan.put.cie.ts.mcloudcore.rest.request.FilterRequest

@PublicAPI
@RestController
@RequestMapping("mongo")
class MongoController (
        @Autowired
        val app: Mcloud
) {
    @ApiOperation(value = "Perform basic queries on selected MongoDB instance. Should be used only to peek the data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "results of query as list of objects", response = List::class)
    ])
    @GetMapping("filter")
    fun filter(@ApiParam("query to perform in standard Mongo JSON format") @RequestBody filter: FilterRequest): ResponseEntity<List<Map<String, Any>>> = ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(app.filterMongo(filter.id, filter.db, filter.collection, filter.filter))
}