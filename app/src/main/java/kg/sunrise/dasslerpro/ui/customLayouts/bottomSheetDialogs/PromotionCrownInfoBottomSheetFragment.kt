package kg.sunrise.dasslerpro.ui.customLayouts.bottomSheetDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.sunrise.dasslerpro.base.fragment.BaseBottomSheetDialogFragment
import kg.sunrise.dasslerpro.databinding.BottomSheetPromotionCrownInfoFragmentBinding

class PromotionCrownInfoBottomSheetFragment(
    private val title: String,
    private val description: String
) :
    BaseBottomSheetDialogFragment<BottomSheetPromotionCrownInfoFragmentBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetPromotionCrownInfoFragmentBinding {
        return BottomSheetPromotionCrownInfoFragmentBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnOk.setOnClickListener {
                dismiss()
            }

            ivClose.setOnClickListener {
                dismiss()
            }

            tvTitle.text = title
            tvDesc.text = description
        }
    }
}