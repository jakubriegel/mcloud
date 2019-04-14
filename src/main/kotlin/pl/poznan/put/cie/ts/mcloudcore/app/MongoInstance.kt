package pl.poznan.put.cie.ts.mcloudcore.app

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.Instant

@ApiModel(description = "Details about selected MongoDB instance")
data class MongoInstance (
        @ApiModelProperty(notes = "name of MongoDB instance")
        val name: String,
        @ApiModelProperty(notes = "id of MongoDB instance")
        val id: String,
        @ApiModelProperty(notes = "date of creation of MongoDB instance")
        val created: Instant,
        @ApiModelProperty(notes = "state of MongoDB container")
        val state: String,
        @ApiModelProperty(notes = "port of MongoDB instance")
        val port: Int
)


