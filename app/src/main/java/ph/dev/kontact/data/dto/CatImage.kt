package ph.dev.kontact.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatImage(
    val id: String,
    val url: String
)