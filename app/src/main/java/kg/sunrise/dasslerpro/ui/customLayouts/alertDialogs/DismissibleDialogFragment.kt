package kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import kg.sunrise.dasslerpro.base.fragment.BaseAlertDialogFragment
import kg.sunrise.dasslerpro.databinding.DialogDismissibleFragmentBinding

class DismissibleDialogFragment(
    @StringRes
    private val textRes: Int,
    @StringRes
    private val btnTextRes: Int,
    private val onSuccessBtnClick: (() -> Unit)
) : BaseAlertDialogFragment<DialogDismissibleFragmentBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogDismissibleFragmentBinding {
        return DialogDismissibleFragmentBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.setText(textRes)
        binding.btnOk.setText(btnTextRes)

        binding.btnOk.setOnClickListener {
            onSuccessBtnClick()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}