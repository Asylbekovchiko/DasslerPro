package kg.sunrise.dasslerpro.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kg.sunrise.dasslerpro.BuildConfig
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.UserInfoResponse
import kg.sunrise.dasslerpro.databinding.FragmentProfileBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs.DismissibleDialogFragment
import kg.sunrise.dasslerpro.ui.preface.preface.PrefaceFragmentDirections
import kg.sunrise.dasslerpro.utils.extensions.gone
import kg.sunrise.dasslerpro.utils.extensions.navigateToMain
import kg.sunrise.dasslerpro.utils.preference.deleteToken
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentWithVM<FragmentProfileBinding, AuthorizationViewModel>() {

    override val viewModel: AuthorizationViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    private var userInfo: UserInfoResponse? = null

    override fun makeRequests() {
        viewModel.getUserInfo()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is UserInfoResponse -> {
                userInfo = data
                updateUserInfoUI(data)
            }
        }
    }

    private fun updateUserInfoUI(userInfoResponse: UserInfoResponse) {
        binding.apply {
            tiPatronymicName.setInputText(userInfoResponse.patronymicName ?: "")
            tiLastName.setInputText(userInfoResponse.lastName)
            tiName.setInputText(userInfoResponse.name)
            tiPhoneNumber.setInputText(userInfoResponse.phoneNumber)
        }
    }

    override fun successRequest() {
        deleteToken(requireContext())
        setSelectedMainMenu()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }

    override fun init() {
        initUI()
        initListeners()
    }

    private fun initListeners() {
        binding.btnExit.setOnClickListener {
            showExitDialog()

        }

        binding.ivSettings.setOnClickListener {
            userInfo?.let {
                navigateToEditProfile()
            }
        }
    }

    private fun showExitDialog() {
        DismissibleDialogFragment(R.string.Really_want_to_exit, R.string.Exit) {
            // todo: in release 2 have to remove reg ID
//            viewModel.removeRegistrationId()
            deleteToken(requireContext())
            navigateToMain()
        }.show(parentFragmentManager, null)
    }

    private fun initUI() {

        binding.inclToolbar.apply {
            ivBack.gone()
            tvToolbar.setText(R.string.Profile)
        }

        binding.apply {
            tiLastName.setInputTextFocusable(false)
            tiName.setInputTextFocusable(false)
            tiPatronymicName.setInputTextFocusable(false)
            tiPhoneNumber.setInputTextFocusable(false)

            tvVersion.text = getString(R.string.version_number, BuildConfig.VERSION_NAME)
        }
    }

    private fun setSelectedMainMenu() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.main_nav_graph
    }

    private fun navigateToEditProfile() {
        userInfo?.let {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(it)
            findNavController().navigate(action)
        }
    }
}