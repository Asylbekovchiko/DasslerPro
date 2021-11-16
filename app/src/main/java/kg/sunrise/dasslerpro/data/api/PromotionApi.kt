package kg.sunrise.dasslerpro.data.api

import kg.sunrise.dasslerpro.data.models.responses.CouponInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionDetailResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionsResponse
import retrofit2.Response
import retrofit2.http.*

interface PromotionApi {

    @GET("promotions/")
    suspend fun getPromotions(
        @Query("page") page: Int
    ): Response<PromotionsResponse>

    @GET("promotions/{id}/")
    suspend fun getPromotionInfo(
        @Path("id") promotionId: Int
    ): Response<PromotionDetailResponse>

    @FormUrlEncoded
    @POST("promotions/activation/")
    suspend fun activateCoupon(
        @Field("promotions") promotionID: Int,
        @Field("code") couponCode: String
    ): Response<CouponInfoResponse>
}