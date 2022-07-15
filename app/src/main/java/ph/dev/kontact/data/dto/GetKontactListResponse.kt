package ph.dev.kontact.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KontactListResponse(@SerialName("contacts") val kontactList: List<KontactInfo>)