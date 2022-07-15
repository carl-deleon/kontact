package ph.dev.kontact.data.remote

import ph.dev.kontact.data.dto.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface KatApi {

    @GET("images/search")
    suspend fun getRandomCats(
        @Query("limit") limit: String,
        @Query("size") size: String = "thumb"
    ): List<CatImage>
}