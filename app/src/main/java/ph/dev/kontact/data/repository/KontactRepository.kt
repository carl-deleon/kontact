package ph.dev.kontact.data.repository

import ph.dev.kontact.data.NetworkModule
import ph.dev.kontact.data.dto.AddKontactRequest
import ph.dev.kontact.data.dto.EditKontactRequest
import ph.dev.kontact.data.dto.KontactInfo
import ph.dev.kontact.data.model.KontactDetail

object KontactRepository {

    private val kontactApi = NetworkModule.kontactApi()

    suspend fun getKontacts(): List<KontactDetail> {
        val list = kontactApi.getKontactList()

        return list.map { it.toKontactDetail() }
    }

    suspend fun addKontact(request: AddKontactRequest): KontactDetail {
        return kontactApi.addNewKontact(request).toKontactDetail()
    }

    suspend fun deleteKontact(id: String) {
        return kontactApi.deleteKontact(id)
    }

    suspend fun editKontact(id: String, request: EditKontactRequest): KontactDetail {
        return kontactApi.updateKontact(id, request).toKontactDetail()
    }

    private fun KontactInfo.toKontactDetail(): KontactDetail {
        return KontactDetail(
            id = id.toString(),
            name = name,
            companyName = companyName,
            contactNumber = phoneNumber,
            emailAddress = emailAddress,
            profileImage = profileImage
        )
    }
}