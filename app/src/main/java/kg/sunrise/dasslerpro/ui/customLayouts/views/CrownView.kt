package kg.sunrise.dasslerpro.ui.customLayouts.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kg.sunrise.dasslerpro.R

class CrownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val tvAmount: TextView
    private val ivCrown: ImageView

    init {
        LayoutInflater.from(context).inflate(
            R.layout.view_crown,
            this,
            true
        )

        tvAmount = findViewById(R.id.tv_amount)
        ivCrown = findViewById(R.id.iv_crown)

        val styledAttrs =
            context.obtainStyledAttributes(attrs, R.styleable.CrownView)

        tvAmount.text = styledAttrs.getString(R.styleable.CrownView_text)
        ivCrown.setImageDrawable(styledAttrs.getDrawable(R.styleable.CrownView_icon))
        tvAmount.setTextColor(styledAttrs.getColorStateList(R.styleable.CrownView_textColor))

        styledAttrs.recycle()
    }

    fun setText(amount: String) {
        tvAmount.text = amount
    }
}