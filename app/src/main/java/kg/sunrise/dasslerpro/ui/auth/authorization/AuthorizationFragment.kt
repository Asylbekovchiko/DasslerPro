package kg.sunrise.dasslerpro.ui.auth.authorization

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.databinding.FragmentAuthorizationBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.utils.constants.Authorization
import kg.sunrise.dasslerpro.utils.constants.PHONE_NUMBER
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import org.koin.android.viewmodel.ext.android.viewModel

class AuthorizationFragment :
    BaseFragmentWithVM<FragmentAuthorizationBinding, AuthorizationViewModel>() {

    override val viewModel: AuthorizationViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    override fun makeRequests() {
        viewModel.checkInternetConnection()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is String -> {
                if (data == PHONE_NUMBER) {
                    navigateToCodeConfirmation(binding.tiPhoneNumber.getPhoneNumber())
                }
            }
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater)
    }

    override fun init() {
        initListeners()
    }

    private fun initListeners() {
        binding.inclToolbar.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendAuthorizationPhoneNumber(binding.tiPhoneNumber.getPhoneNumber())
        }

        binding.tiPhoneNumber.isPhoneNumberValid.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSend.setEnabled(R.color.purple_7358A7)
            } else {
                binding.btnSend.setDisabled(R.color.gray_light_CBCBCB)
            }
        }
    }

    private fun navigateToCodeConfirmation(phoneNumber: String) {
        val action =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToCodeConfirmationFragment(
                Authorization.REGISTRATION,
                phoneNumber
            )
        findNavController().navigate(action)
    }
}