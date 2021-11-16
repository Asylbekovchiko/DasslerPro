package kg.sunrise.dasslerpro.data.repositories

import kg.sunrise.dasslerpro.base.repository.BaseRepository
import kg.sunrise.dasslerpro.data.api.AuthorizationApi
import kg.sunrise.dasslerpro.data.models.responses.ConfirmResponse
import kg.sunrise.dasslerpro.data.models.responses.UserInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse
import okhttp3.ResponseBody
import retrofit2.Response

class AuthorizationRepository(
    private val authorizationApi: AuthorizationApi
) : BaseRepository() {

    suspend fun sendAuthorizationPhoneNumber(phoneNumber: String): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.sendAuthorizationPhoneNumber(phoneNumber)
        }
    }

    suspend fun sendConfirmationCode(phoneNumber: String, code: String): Response<ConfirmResponse>? {
        return makeRequest {
            authorizationApi.sendConfirmationCode(phoneNumber, code)
        }
    }

    suspend fun fillUserInfo(token: String, userInfo: UserRequestResponse): Response<UserRequestResponse>? {
        return makeRequest {
            authorizationApi.fillUserInfo(token, userInfo)
        }
    }

    suspend fun updateUserInfo(userInfo: UserRequestResponse): Response<UserRequestResponse>? {
        return makeRequest {
            authorizationApi.updateUserInfo(userInfo)
        }
    }

    suspend fun getUserInfo(): Response<UserInfoResponse>? {
        return makeRequest {
            authorizationApi.getUserInfo()
        }
    }

    suspend fun removeRegistrationId(registrationId: String): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.removeRegistrationId(registrationId)
        }
    }

    suspend fun sendConfirmCodeToOldPhone(): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.sendConfirmCodeToOldPhone()
        }
    }

    suspend fun confirmOldPhoneNumber(confirmCode: String): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.confirmOldPhoneNumber(confirmCode)
        }
    }

    suspend fun changeOldPhoneNumber(phoneNumber: String): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.changeOldPhoneNumber(phoneNumber)
        }
    }

    suspend fun confirmNewPhoneNumber(confirmationCode: String): Response<ResponseBody>? {
        return makeRequest {
            authorizationApi.confirmNewPhoneNumber(confirmationCode)
        }
    }
}