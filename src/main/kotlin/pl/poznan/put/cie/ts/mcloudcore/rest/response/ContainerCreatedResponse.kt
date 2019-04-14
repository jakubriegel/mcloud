package pl.poznan.put.cie.ts.mcloudcore.rest.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Basic data of newly created MongoDB instance")
data class ContainerCreatedResponse (
        @ApiModelProperty(notes = "id of newly created MongoDB instance")
        val id: String
)