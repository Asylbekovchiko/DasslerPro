package kg.sunrise.dasslerpro.ui.main.profile.profileEditFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse
import kg.sunrise.dasslerpro.databinding.FragmentProfileEditBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs.DismissibleDialogFragment
import kg.sunrise.dasslerpro.ui.main.profile.ProfileViewModel
import kg.sunrise.dasslerpro.utils.constants.SEND_CONFIRM_CODE
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileEditFragment :
    BaseFragmentWithVM<FragmentProfileEditBinding, AuthorizationViewModel>() {

    private val navArgs by navArgs<ProfileEditFragmentArgs>()

    private val profileEditViewModel by viewModel<ProfileViewModel>()

    override val viewModel: AuthorizationViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    override fun makeRequests() {
        viewModel.checkInternetConnection()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is UserRequestResponse -> {
                navigateBack()
            }
            SEND_CONFIRM_CODE -> {
                navigateToChangePhoneNumber(navArgs.userInfo.phoneNumber)
            }
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileEditBinding {
        return FragmentProfileEditBinding.inflate(inflater)
    }

    override fun init() {
        setUpObservables()
        initUI()
        initListeners()
    }

    private fun setUpObservables() {
        val userInfo = navArgs.userInfo

        profileEditViewModel.apply {
            isPrivacyNeeded = false

            lastName.value = userInfo.lastName
            userName.value = userInfo.name
            patronymicName.value = userInfo.patronymicName

            isValid.observe(viewLifecycleOwner) {
                if (it == true) setEnableBtn()
                else setDisableBtn()
            }
        }
    }

    private fun initListeners() {
        binding.inclToolbar.ivBack.setOnClickListener {
            navigateBack()
        }

        binding.tiPhoneNumber.setOnIconClickListener {
            DismissibleDialogFragment(R.string.Phone_number_change_check, R.string.Next) {
                viewModel.sendConfirmCodeToOldPhone()
            }.show(parentFragmentManager, null)
        }

        binding.btnSave.setOnClickListener {
            viewModel.updateUserInfo(profileEditViewModel.getUserModel())
        }

        binding.tiLastName.addTextChangedListener {
            profileEditViewModel.lastName.value = it.text.toString()
        }

        binding.tiName.addTextChangedListener {
            profileEditViewModel.userName.value = it.text.toString()
        }

        binding.tiPatronymicName.addTextChangedListener {
            profileEditViewModel.patronymicName.value = it.text.toString()
        }
    }

    private fun navigateToChangePhoneNumber(phoneNumber: String) {
        val action = ProfileEditFragmentDirections
            .actionProfileEditFragmentToNumberChangeNavGraph(phoneNumber)
        findNavController().navigate(action)
    }

    private fun initUI() {
        binding.inclToolbar.tvToolbar.setText(R.string.Profile)
        binding.tiPhoneNumber.setInputTextFocusable(false)

        binding.apply {
            val userInfo = navArgs.userInfo
            tiPhoneNumber.setInputText(userInfo.phoneNumber)
            tiLastName.setInputText(userInfo.lastName)
            tiName.setInputText(userInfo.name)
            tiPatronymicName.setInputText(userInfo.patronymicName ?: "")
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun setEnableBtn() {
        binding.btnSave.setEnabled(R.color.purple_7358A7)
    }

    private fun setDisableBtn() {
        binding.btnSave.setDisabled(R.color.gray_light_CBCBCB)
    }
}