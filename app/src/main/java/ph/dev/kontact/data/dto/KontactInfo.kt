package ph.dev.kontact.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KontactInfo(
    val id: Int,
    val name: String,
    @SerialName("email_address") val emailAddress: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("company_name") val companyName: String,
    @SerialName("date_added") val dateAdded: String,
    @SerialName("profile_image") val profileImage: String
)