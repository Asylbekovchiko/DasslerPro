package kg.sunrise.dasslerpro.data.api

import kg.sunrise.dasslerpro.data.models.responses.AboutUsInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.AppVersionResponse
import kg.sunrise.dasslerpro.data.models.responses.HandbookResponse
import kg.sunrise.dasslerpro.data.models.responses.PrivacyPoliceResponse
import retrofit2.Response
import retrofit2.http.GET

interface InfoApi {

    @GET("info/handbook/")
    suspend fun getHandbooks(): Response<HandbookResponse>

    @GET("info/privacy-policy/")
    suspend fun getPrivacyInfo(): Response<PrivacyPoliceResponse>

    @GET("info/about/")
    suspend fun getAboutUsInfo(): Response<AboutUsInfoResponse>

    @GET("setting/version/")
    suspend fun getVersion(): Response<AppVersionResponse>

}