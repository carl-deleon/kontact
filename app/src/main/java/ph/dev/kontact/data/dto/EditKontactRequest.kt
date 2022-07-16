package ph.dev.kontact.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditKontactRequest(
    val name: String,
    val emailAddress: String,
    val phoneNumber: String,
    val companyName: String
)