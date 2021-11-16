package kg.sunrise.dasslerpro.data.models.responses

import com.google.gson.annotations.SerializedName

data class UserRequestResponse(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middle_name: String?
)
