package ph.dev.kontact.ui.edit

import ph.dev.kontact.data.model.KontactDetail

data class EditKontactParam(
    val name: String,
    val emailAddress: String,
    val phoneNumber: String,
    val companyName: String
) {

    companion object {

        fun from(kontactDetail: KontactDetail): EditKontactParam {
            return EditKontactParam(
                name = kontactDetail.name,
                emailAddress = kontactDetail.emailAddress,
                phoneNumber = kontactDetail.contactNumber,
                companyName = kontactDetail.companyName
            )
        }
    }
}