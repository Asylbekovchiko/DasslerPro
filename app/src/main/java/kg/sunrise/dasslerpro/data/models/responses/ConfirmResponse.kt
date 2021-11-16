package kg.sunrise.dasslerpro.data.models.responses

import com.google.gson.annotations.SerializedName

data class ConfirmResponse(
    @SerializedName("token")
    val token : String,
    @SerializedName("is_profile_completed")
    val isProfileCompleted : Boolean
)
