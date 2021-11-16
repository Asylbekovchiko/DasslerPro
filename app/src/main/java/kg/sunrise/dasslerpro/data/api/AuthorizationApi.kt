package kg.sunrise.dasslerpro.data.api

import kg.sunrise.dasslerpro.data.models.responses.ConfirmResponse
import kg.sunrise.dasslerpro.data.models.responses.UserInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthorizationApi {

    @FormUrlEncoded
    @POST("account/auth/")
    suspend fun sendAuthorizationPhoneNumber(
        @Field("phone") phoneNumber: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("account/login-confirm/")
    suspend fun sendConfirmationCode(
        @Field("phone") phoneNumber: String,
        @Field("confirmation_code") code: String
    ): Response<ConfirmResponse>

    @PATCH("account/")
    suspend fun fillUserInfo(
        @Header("Authorization") token: String,
        @Body userInfo: UserRequestResponse
    ): Response<UserRequestResponse>

    @PATCH("account/")
    suspend fun updateUserInfo(
        @Body userInfo: UserRequestResponse
    ): Response<UserRequestResponse>

    @GET("account/data/")
    suspend fun getUserInfo(): Response<UserInfoResponse>


    @DELETE("account/device/{registration_id}/")
    suspend fun removeRegistrationId(
        @Path("registration_id") registrationId: String
    ): Response<ResponseBody>


    @GET("account/sms-to-old-phone/")
    suspend fun sendConfirmCodeToOldPhone(): Response<ResponseBody>

    @FormUrlEncoded
    @POST("account/old-phone-confirm/")
    suspend fun confirmOldPhoneNumber(
        @Field("confirmation_code") confirmCode: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("account/change-old-phone")
    suspend fun changeOldPhoneNumber(
        @Field("phone") phoneNumber: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("account/new-phone-confirm/")
    suspend fun confirmNewPhoneNumber(
        @Field("confirmation_code") confirmationCode: String
    ): Response<ResponseBody>
}