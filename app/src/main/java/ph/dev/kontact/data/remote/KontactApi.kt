package ph.dev.kontact.data.remote

import ph.dev.kontact.data.dto.AddKontactRequest
import ph.dev.kontact.data.dto.KontactInfo
import retrofit2.http.*

interface KontactApi {

    @GET("contacts")
    suspend fun getKontactList(): List<KontactInfo>

    @GET("contacts/{id}")
    suspend fun getKontact(@Path("id") id: String): KontactInfo

    @POST("contacts")
    suspend fun addNewKontact(@Body request: AddKontactRequest): KontactInfo

    @PUT("contacts/{id}")
    suspend fun updateKontact(@Path("id") id: String, info: KontactInfo)

    @DELETE("contacts/{id}")
    suspend fun deleteKontact(@Path("id") id: String, info: KontactInfo)
}