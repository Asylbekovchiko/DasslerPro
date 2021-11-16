package kg.sunrise.dasslerpro.ui.preface.preface

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kg.sunrise.dasslerpro.BuildConfig
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.AppVersionResponse
import kg.sunrise.dasslerpro.databinding.FragmentPrefaceBinding
import kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs.UpdateVersionDialogFragment
import kg.sunrise.dasslerpro.ui.preface.PrefaceViewModel
import kg.sunrise.dasslerpro.utils.constants.PREFACE_DURATION
import kg.sunrise.dasslerpro.utils.extensions.navigateToMain
import kg.sunrise.dasslerpro.utils.preference.isUserEnterFirstTime
import kg.sunrise.dasslerpro.utils.preference.setUserEntered
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class PrefaceFragment : BaseFragmentWithVM<FragmentPrefaceBinding, PrefaceViewModel>() {

    override val viewModel: PrefaceViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy { binding.inclProgress.clProgress }

    override var isToShowProgress = false

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPrefaceBinding {
        return FragmentPrefaceBinding.inflate(inflater)
    }

    override fun init() {
        startDelay()
    }

    private fun startDelay() {
        lifecycleScope.launch {
            delay(PREFACE_DURATION)
            viewModel.isDelayEnd = true
            navigateFurther()
        }
    }

    override fun makeRequests() {
        viewModel.getVersion()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is AppVersionResponse -> {
                handleAppVersion(data)
            }
        }
    }

    private fun handleAppVersion(version: AppVersionResponse) {
        if (version.androidVersion != BuildConfig.VERSION_NAME) {
            UpdateVersionDialogFragment(version.androidVersion, version.androidForceUpdate) {
                viewModel.isVersionValid = true
                navigateFurther()
            }
                .show(parentFragmentManager, null)
        } else {
            viewModel.isVersionValid = true
            navigateFurther()
        }
    }

    override fun successRequest() {

    }

    private fun navigateToSlider() {
        val action = PrefaceFragmentDirections.actionPrefaceFragmentToSliderFragment()
        findNavController().navigate(action)
    }

    private fun navigateFurther() {
        if (!viewModel.isVersionValid || !viewModel.isDelayEnd) {
            return
        } else if (isUserEnterFirstTime(requireContext())) {
            setUserEntered(requireContext())
            navigateToSlider()
        } else {
            navigateToMain()
        }
    }
}

