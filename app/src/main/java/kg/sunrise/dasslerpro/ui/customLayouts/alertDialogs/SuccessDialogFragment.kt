package kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kg.sunrise.dasslerpro.base.fragment.BaseAlertDialogFragment
import kg.sunrise.dasslerpro.databinding.DialogSuccessFragmentBinding
import kg.sunrise.dasslerpro.utils.extensions.visible


class SuccessDialogFragment(
    @DrawableRes
    private val iconRes: Int,
    private val text: String,
    @StringRes
    private val iconTextRes: Int? = null,
    private val onBtnClick: (() -> Unit)? = null
) : BaseAlertDialogFragment<DialogSuccessFragmentBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogSuccessFragmentBinding {
        return DialogSuccessFragmentBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivIcon.setImageResource(iconRes)
        binding.tvDesc.text = text

        iconTextRes?.let { iconTextRes ->
            binding.apply {
                gIcon.visible()
                tvIconDesc.setText(iconTextRes)
            }
        }

        binding.btnSuccess.setOnClickListener {
            onBtnClick?.invoke()
            dismiss()
        }
    }
}