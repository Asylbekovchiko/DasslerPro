package kg.sunrise.dasslerpro.data.models.responses

import com.google.gson.annotations.SerializedName

data class AppVersionResponse(
    @SerializedName("android_version")
    val androidVersion: String,
    @SerializedName("android_force_update")
    val androidForceUpdate: Boolean
)
