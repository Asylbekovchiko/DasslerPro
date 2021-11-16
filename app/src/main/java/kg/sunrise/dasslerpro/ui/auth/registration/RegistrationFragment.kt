package kg.sunrise.dasslerpro.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse
import kg.sunrise.dasslerpro.databinding.FragmentRegistrationBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.ui.main.profile.ProfileViewModel
import kg.sunrise.dasslerpro.utils.extensions.navigateToMain
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import kg.sunrise.dasslerpro.utils.preference.setToken
import org.koin.android.viewmodel.ext.android.viewModel

class RegistrationFragment :
    BaseFragmentWithVM<FragmentRegistrationBinding, AuthorizationViewModel>() {

    private val navArgs by navArgs<RegistrationFragmentArgs>()
    private val registrationViewModel: ProfileViewModel by viewModel()

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
                setToken(requireContext(), navArgs.token)
                navigateToMain()
            }
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater)
    }

    override fun init() {
        initUI()
        initListeners()
        setUpMediatorLiveData()
    }

    private fun setUpMediatorLiveData() {
        registrationViewModel.isValid.observe(viewLifecycleOwner) {
            if (it == true) {
                setEnableBtn()
            } else {
                setDisableBtn()
            }
        }
    }

    private fun initListeners() {
        binding.inclToolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener {
            viewModel.fillUserInfo(
                navArgs.token, registrationViewModel.getUserModel())
        }

        binding.tvPrivacy.setOnClickListener {
            navigateToPolicy()
        }

        binding.cbPrivacy.setOnCheckedChangeListener { buttonView, isChecked ->
            registrationViewModel.isPrivacyCheck.value = isChecked
        }

        binding.tiLastName.addTextChangedListener {
            registrationViewModel.lastName.value = it.text.toString()
        }

        binding.tiName.addTextChangedListener {
            registrationViewModel.userName.value = it.text.toString()
        }

        binding.tiPatronymicName.addTextChangedListener {
            registrationViewModel.patronymicName.value = it.text.toString()
        }
    }

    private fun initUI() {
        binding.inclToolbar.tvToolbar.setText(R.string.Registration)
        binding.tiPhoneNumber.setInputText(navArgs.phoneNumber)
        binding.tiPhoneNumber.setInputTextFocusable(false)
    }

    private fun setEnableBtn() {
        binding.btnNext.setEnabled(R.color.purple_7358A7)
    }

    private fun setDisableBtn() {
        binding.btnNext.setDisabled(R.color.gray_light_CBCBCB)
    }

    private fun navigateToPolicy() {
        val action =
            RegistrationFragmentDirections.actionRegistrationFragmentToPrivacyPolicyFragment()
        findNavController().navigate(action)
    }
}