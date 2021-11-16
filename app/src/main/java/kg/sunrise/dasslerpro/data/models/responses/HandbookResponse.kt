package kg.sunrise.dasslerpro.data.models.responses

import com.google.gson.annotations.SerializedName

data class HandbookResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("handbook_questions")
    val handbookQuestions: ArrayList<HandbookQuestion>
)

data class HandbookQuestion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("handbook_question_answer")
    val handbookQuestionAnswer: HandbookAnswer
)

data class HandbookAnswer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("answer")
    val answer: String
)