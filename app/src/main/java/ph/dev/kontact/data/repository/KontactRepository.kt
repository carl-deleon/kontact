package ph.dev.kontact.data.repository

import ph.dev.kontact.data.NetworkModule
import ph.dev.kontact.data.model.KontactDetail

object KontactRepository {

    private val kontactApi = NetworkModule.kontactApi()
    private val katApi = NetworkModule.katApi()

    suspend fun getKontacts(): List<KontactDetail> {
        val list = kontactApi.getKontactList()

        val images = katApi.getRandomCats(list.size.toString())

        return list.mapIndexed { index, kontactInfo ->
            KontactDetail(
                id = kontactInfo.id.toString(),
                name = kontactInfo.name,
                companyName = kontactInfo.companyName,
                contactNumber = kontactInfo.phoneNumber,
                emailAddress = kontactInfo.emailAddress,
                dateAdded = kontactInfo.dateAdded,
                profileImage = images[index].url
            )
        }
    }
}