package kg.sunrise.dasslerpro.data.models.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfoResponse(
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val patronymicName: String?,
    @SerializedName("coupon")
    val coupon: CouponResponse
) : Serializable

data class CouponResponse(
    @SerializedName("premium")
    val premiumCount: Int,
    @SerializedName("gold")
    val goldCount: Int,
    @SerializedName("silver")
    val silverCount: Int
) : Serializable
