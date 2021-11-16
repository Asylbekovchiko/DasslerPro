package kg.sunrise.dasslerpro.data.models.responses


import com.google.gson.annotations.SerializedName
import kg.sunrise.dasslerpro.dto.SocialDto

data class AboutUsInfoResponse(
    @SerializedName("about_images")
    val images: ArrayList<AboutImage>,
    @SerializedName("about_phones")
    val phones: ArrayList<AboutPhone>,
    @SerializedName("about_socials")
    val socials: ArrayList<AboutSocial>,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)

data class AboutImage(
    val id: Int,
    val image: String
)

data class AboutSocial(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("logo")
    val logo: String
) {
    fun getSocialDto() = SocialDto(
        link,
        logo
    )
}

data class AboutPhone(
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String
)