package kg.sunrise.dasslerpro.ui.main.handbook

import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH
import kg.sunrise.dasslerpro.data.models.responses.HandbookQuestion

class HandbookVH(
    parent: ViewGroup,
    @LayoutRes layoutResId: Int
) : BaseVH<HandbookQuestion>(parent, layoutResId) {

    private val tvTitle =
        itemView.findViewById<TextView>(R.id.tv_title)
    private val tvDescription =
        itemView.findViewById<TextView>(R.id.tv_description)

    override fun bind(item: HandbookQuestion) {
        tvTitle.text = item.question
        tvDescription.text = item.handbookQuestionAnswer.answer
    }

}