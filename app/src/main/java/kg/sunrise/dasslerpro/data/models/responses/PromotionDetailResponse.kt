package kg.sunrise.dasslerpro.data.models.responses


import com.google.gson.annotations.SerializedName
import kg.sunrise.dasslerpro.dto.SocialDto

data class PromotionDetailResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("partner")
    val partner: Partner,
    @SerializedName("promotion_images")
    val promotionsImages: ArrayList<PromotionsImage>,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("title")
    val title: String
)

data class Partner(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("partner_socials")
    val partnerSocials: ArrayList<PartnerSocial>,
    @SerializedName("title")
    val title: String
)

data class PromotionsImage(
    @SerializedName("image")
    val image: String,
    @SerializedName("is_main")
    val isMain: Boolean
)

data class PartnerSocial(
    @SerializedName("link")
    val link: String,
    @SerializedName("logo")
    val image: String
) {
    fun getSocialDto() = SocialDto(
        link,
        image
    )
}