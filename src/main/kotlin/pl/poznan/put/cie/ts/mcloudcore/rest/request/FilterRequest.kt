package pl.poznan.put.cie.ts.mcloudcore.rest.request

data class FilterRequest (
        val id: String,
        val db: String,
        val collection: String,
        val filter: Map<String, Any>
)