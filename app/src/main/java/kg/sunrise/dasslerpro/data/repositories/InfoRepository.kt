package kg.sunrise.dasslerpro.data.repositories

import kg.sunrise.dasslerpro.base.repository.BaseRepository
import kg.sunrise.dasslerpro.data.api.InfoApi

class InfoRepository(
    private val api: InfoApi
) : BaseRepository() {

    suspend fun getHandbooks() = makeRequest {
        api.getHandbooks()
    }

    suspend fun getPrivacyInfo() = makeRequest {
        api.getPrivacyInfo()
    }

    suspend fun getInfoAboutUs() = makeRequest {
        api.getAboutUsInfo()
    }
}