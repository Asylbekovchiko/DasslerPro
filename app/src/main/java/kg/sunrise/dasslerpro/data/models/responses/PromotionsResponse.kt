package kg.sunrise.dasslerpro.data.models.responses


import com.google.gson.annotations.SerializedName

data class PromotionsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Int?,
    @SerializedName("previous")
    val previous: Int?,
    @SerializedName("results")
    val promotions: ArrayList<PromotionResponse>,
    @SerializedName("coupon_info")
    var couponInfo: PromotionCoupon?
)

data class PromotionResponse(
    @SerializedName("id")
    val promotionID: Int,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("gold_count")
    val goldCount: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("premium_count")
    val premiumCount: Int?,
    @SerializedName("standard_count")
    val standardCount: Int?,
    @SerializedName("title")
    val title: String,
    var couponInfo: PromotionCoupon?
)

data class PromotionCoupon(
    val title: String,
    val description: String
)