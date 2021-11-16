package kg.sunrise.dasslerpro.ui.customLayouts.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.utils.extensions.getColorCompat
import kg.sunrise.dasslerpro.utils.extensions.gone
import kg.sunrise.dasslerpro.utils.extensions.hideKeyboard
import kg.sunrise.dasslerpro.utils.extensions.visible

open class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    protected lateinit var clLabelContainer: ConstraintLayout
    protected lateinit var tvLabel: TextView
    protected lateinit var tvStar: TextView
    protected lateinit var etInput: EditText
    protected lateinit var tvError: TextView
    protected lateinit var ivIcon: ImageView

    protected val defaultErrorMessage: String

    init {
        inflateView()

        val styledAttrs =
            context.obtainStyledAttributes(attrs, R.styleable.TextInputView)

        if (styledAttrs.hasValue(R.styleable.TextInputView_label)) {
            clLabelContainer.visible()

            tvLabel.text = styledAttrs.getString(R.styleable.TextInputView_label)

            tvStar.visibility = if (styledAttrs.getBoolean(
                    R.styleable.TextInputView_isRequired,
                    false
                )
            ) View.VISIBLE else View.GONE
        }

        defaultErrorMessage = styledAttrs.getString(R.styleable.TextInputView_errorText) ?: ""

        setUpEditText(styledAttrs)

        if (styledAttrs.hasValue(R.styleable.TextInputView_editTextIcon)) {
            ivIcon.setImageDrawable(styledAttrs.getDrawable(R.styleable.TextInputView_editTextIcon))
            ivIcon.visible()
        }

        styledAttrs.recycle()
    }

    fun setInputText(text: String) {
        etInput.setText(text)
    }

    fun getInputText(): String {
        return etInput.text.toString()
    }

    fun setInputTextFocusable(isFocusable: Boolean) {
        etInput.isFocusable = isFocusable
    }

    fun addTextChangedListener(onChange: (editText: EditText) -> Unit) {
        etInput.addTextChangedListener {
            onChange(etInput)
        }
    }

    fun setOnIconClickListener(onClick: () -> Unit) {
        ivIcon.setOnClickListener {
            onClick()
        }
    }

    fun showError(message: String? = null) {
        val errorMessage = message ?: defaultErrorMessage
        tvError.text = errorMessage
        setErrorViewsColor()
    }

    fun hideError() {
        setDefaultViewsColor()
    }

    fun hideKeyboard() {
        etInput.hideKeyboard()
    }

    fun disableEditText() {
        etInput.isEnabled = false
    }

    fun enableEditText() {
        etInput.isEnabled = true
    }

    private fun setDefaultViewsColor() {
        setViewsColor(R.color.gray_B1B1B1)
        etInput.setBackgroundResource(R.drawable.shape_rad_8dp_border_1dp_gray_e0e0e0)
        tvError.gone()
    }

    private fun setErrorViewsColor() {
        setViewsColor(R.color.red_FF1B1B)
        etInput.setBackgroundResource(R.drawable.shape_rad_8dp_border_1dp_red_ff1b1b)
        tvError.visible()
    }

    private fun setViewsColor(@ColorRes colorRes: Int) {
        tvLabel.setTextColor(context.getColorCompat(colorRes))
        tvStar.setTextColor(context.getColorCompat(colorRes))
    }

    protected open fun inflateView() {
        LayoutInflater.from(context).inflate(
            R.layout.view_text_input,
            this,
            true
        )

        clLabelContainer = findViewById(R.id.cl_label_container)
        tvLabel = findViewById(R.id.tv_label)
        tvStar = findViewById(R.id.tv_star)
        etInput = findViewById(R.id.et_input)
        tvError = findViewById(R.id.tv_error)
        ivIcon = findViewById(R.id.iv_icon)
    }

    protected open fun setUpEditText(styledAttrs: TypedArray) {
        etInput.hint = styledAttrs.getString(R.styleable.TextInputView_hint)

        if (styledAttrs.hasValue(R.styleable.TextInputView_android_inputType)) {
            val inputType = styledAttrs.getInt(
                R.styleable.TextInputView_android_inputType,
                EditorInfo.TYPE_NULL
            )
            etInput.inputType = inputType
        }
    }
}