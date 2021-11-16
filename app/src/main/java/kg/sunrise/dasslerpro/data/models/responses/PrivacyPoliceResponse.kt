package kg.sunrise.dasslerpro.data.models.responses


import com.google.gson.annotations.SerializedName

data class PrivacyPoliceResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("pdf_file")
    val pdfFileURL: String
)