package ph.dev.kontact.data.remote

import ph.dev.kontact.data.dto.AddKontactRequest
import ph.dev.kontact.data.dto.EditKontactRequest
import ph.dev.kontact.data.dto.KontactInfo
import retrofit2.http.*

interface KontactApi {

    @GET("contacts")
    suspend fun getKontactList(): List<KontactInfo>

    @POST("contacts")
    suspend fun addNewKontact(@Body request: AddKontactRequest): KontactInfo

    @PUT("contacts/{id}")
    suspend fun updateKontact(
        @Path("id") id: String,
        @Body request: EditKontactRequest
    ): KontactInfo

    @DELETE("contacts/{id}")
    suspend fun deleteKontact(@Path("id") id: String)
}