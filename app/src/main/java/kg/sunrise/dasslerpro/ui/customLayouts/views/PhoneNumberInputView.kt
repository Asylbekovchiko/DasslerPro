package kg.sunrise.dasslerpro.ui.customLayouts.views

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.santalu.maskara.Mask
import com.santalu.maskara.MaskChangedListener
import com.santalu.maskara.MaskStyle
import kg.sunrise.dasslerpro.R

open class PhoneNumberInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputView(context, attrs, defStyleAttr) {

    private val _isPhoneNumberValid: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPhoneNumberValid: LiveData<Boolean> = _isPhoneNumberValid

    override fun inflateView() {
        LayoutInflater.from(context).inflate(
            R.layout.view_phone_number_input,
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

    override fun setUpEditText(styledAttrs: TypedArray) {
        val phoneNumberMask = resources.getString(R.string.phone_number_kg_mask)
        etInput.hint = phoneNumberMask
        etInput.inputType = InputType.TYPE_CLASS_PHONE

        val mask = Mask(
            value = phoneNumberMask,
            character = 'x',
            style = MaskStyle.COMPLETABLE
        )

        val listener = MaskChangedListener(mask)
        etInput.addTextChangedListener(listener)
        etInput.addTextChangedListener {
            _isPhoneNumberValid.value = listener.isDone
        }
    }

    fun getPhoneNumber(): String {
        return etInput.text.replace(PHONE_NUMBER_REGEX.toRegex(), "")
    }
}

private const val PHONE_NUMBER_REGEX = "[^0-9+]"