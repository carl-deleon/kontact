package ph.dev.kontact.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KontactDetail(
    val id: String,
    val name: String,
    val profileImage: String,
    val emailAddress: String,
    val contactNumber: String,
    val companyName: String,
) : Parcelable