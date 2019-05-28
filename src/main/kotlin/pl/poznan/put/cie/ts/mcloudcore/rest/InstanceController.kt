package pl.poznan.put.cie.ts.mcloudcore.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.cie.ts.mcloudcore.app.Mcloud
import pl.poznan.put.cie.ts.mcloudcore.app.MongoInstance
import pl.poznan.put.cie.ts.mcloudcore.config.PublicAPI
import pl.poznan.put.cie.ts.mcloudcore.config.SwaggerConfig
import pl.poznan.put.cie.ts.mcloudcore.rest.response.ContainerCreatedResponse

@PublicAPI
@Api(value = "Manage instances of Mongo", description = "Create, start, inspect and destroy instances of MongoDB")
@RestController
@RequestMapping("instance")
class InstanceController (
        @Autowired
        val app: Mcloud
) {

    @ApiOperation(value = "Create new MongoDB instance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = SwaggerConfig.NEVER_RETURNS, response = Any::class),
        ApiResponse(code = 201, message = "Successfully created MongoDB instance", response = ContainerCreatedResponse::class)
    ])
    @PostMapping("new")
    fun createMongoInstance(
            @ApiParam(value = "Desired name of instance", required = true) @RequestParam name: String
    ) = ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(app.createMongoInstance(name))

    @ApiOperation(value = "Start MongoDB instance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "details started MongoDB instance", response = MongoInstance::class)
    ])
    @PostMapping("start")
    fun startMongoInstance(
            @ApiParam(value = "id of instance to start") @RequestParam id: String
    ) = ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(app.startMongoInstance(id))

    @ApiOperation(value = "Get details about specific MongoDB instance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "details about selected MongoDB instance", response = MongoInstance::class)
    ])
    @GetMapping
    fun inspectInstance(
            @ApiParam(value = "id of instance to inspect") @RequestParam id: String
    ) = ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(app.inspectMongoInstance(id))
}