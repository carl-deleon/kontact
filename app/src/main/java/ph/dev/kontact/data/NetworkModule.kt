package ph.dev.kontact.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ph.dev.kontact.BuildConfig
import ph.dev.kontact.data.remote.KatApi
import ph.dev.kontact.data.remote.KontactApi
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber

object NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    fun kontactApi() = retrofit(BuildConfig.API_BASE_URL).create<KontactApi>()

    fun katApi() = retrofit(BuildConfig.KAT_API_BASE_URL).create<KatApi>()

    @OptIn(ExperimentalSerializationApi::class)
    private fun retrofit(baseUrl: String): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient())
            .build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor())
            .build()
    }

    private fun httpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}