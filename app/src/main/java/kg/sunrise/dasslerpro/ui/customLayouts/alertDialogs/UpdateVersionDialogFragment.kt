package kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.sunrise.dasslerpro.BuildConfig
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseAlertDialogFragment
import kg.sunrise.dasslerpro.databinding.DialogUpdateVersionFragmentBinding

class UpdateVersionDialogFragment(
    private val androidVersion: String,
    private val isForceUpdate: Boolean,
    val onDismiss: () -> Unit
) : BaseAlertDialogFragment<DialogUpdateVersionFragmentBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogUpdateVersionFragmentBinding {
        return DialogUpdateVersionFragmentBinding.inflate(inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvUpdateText.text = getString(R.string.app_update_text, androidVersion)
            btnCancel.visibility = if (isForceUpdate) View.GONE else View.VISIBLE

            btnCancel.setOnClickListener {
                onDismiss()
                dismiss()
            }

            btnOk.setOnClickListener {
                openInPlayStore()
            }
        }
    }

    private fun openInPlayStore() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "market://details?id=${BuildConfig.APPLICATION_ID}"
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}}"
                    )
                )
            )
        }
    }
}