package kg.sunrise.dasslerpro.ui.customLayouts.bottomSheetDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseBottomSheetDialogFragment
import kg.sunrise.dasslerpro.data.models.responses.CouponInfoResponse
import kg.sunrise.dasslerpro.databinding.BottomSheetCouponCodeFragmentBinding
import kg.sunrise.dasslerpro.dto.CouponBottomSheetDto
import kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs.SuccessDialogFragment
import kg.sunrise.dasslerpro.utils.constants.COUPON_CODE_LENGTH
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled

class CouponCodeBottomSheetFragment(val activateCouponRequest: (String) -> Unit) :
    BaseBottomSheetDialogFragment<BottomSheetCouponCodeFragmentBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetCouponCodeFragmentBinding {
        return BottomSheetCouponCodeFragmentBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivClose.setOnClickListener {
            if (isCancelable) {
                dismiss()
            }
        }

        binding.btnSend.setOnClickListener {
            binding.vTextInput.apply {
                hideKeyboard()

                val inputText = getInputText()
                if (inputText.length == COUPON_CODE_LENGTH) {
                    isCancelable = false
                    hideError()
                    activateCouponRequest(inputText)
                }
            }
        }

        binding.vTextInput.addTextChangedListener {
            binding.vTextInput.hideError()
            if (binding.vTextInput.getInputText().length == COUPON_CODE_LENGTH) {
                binding.btnSend.setEnabled(R.color.purple_7358A7)
            } else {
                binding.btnSend.setDisabled(R.color.gray_light_CBCBCB)
            }
        }
    }

    fun showError(message: String) {
        isCancelable = true
        binding.vTextInput.showError(message)
    }

    fun success(couponInfo: CouponInfoResponse, navigateToMainPage: () -> Boolean) {
        isCancelable = true
        val coupon = getCoupon(couponInfo)
        SuccessDialogFragment(coupon.iconRes, coupon.text, coupon.iconTextRes) {
            navigateToMainPage()
            dismiss()
        }.show(parentFragmentManager, null)
        dismiss()
    }

    private fun getCoupon(couponInfo: CouponInfoResponse): CouponBottomSheetDto {
        return when (couponInfo.type) {
            1 -> CouponBottomSheetDto(R.drawable.ic_crown_platinum, couponInfo.message, R.string.platinum)
            2 -> CouponBottomSheetDto(R.drawable.ic_crown_golden, couponInfo.message, R.string.golden)
            3 -> CouponBottomSheetDto(R.drawable.ic_crown_bronze, couponInfo.message, R.string.bronze)
            else -> throw Throwable("1716 не верный тип купона")
        }
    }
}