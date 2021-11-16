package kg.sunrise.dasslerpro.data.repositories

import kg.sunrise.dasslerpro.base.repository.BaseRepository
import kg.sunrise.dasslerpro.data.api.InfoApi
import kg.sunrise.dasslerpro.data.models.responses.AppVersionResponse
import retrofit2.Response

class ApplicationInfoRepository(
    private val api: InfoApi
) : BaseRepository() {

    suspend fun getVersion(): Response<AppVersionResponse>? =
        makeRequest {
            api.getVersion()
        }

}