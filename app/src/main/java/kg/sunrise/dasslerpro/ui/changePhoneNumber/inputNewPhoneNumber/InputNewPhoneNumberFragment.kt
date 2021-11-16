package kg.sunrise.dasslerpro.ui.changePhoneNumber.inputNewPhoneNumber

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.databinding.FragmentInputNewPhoneNumberBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.utils.constants.Authorization
import kg.sunrise.dasslerpro.utils.constants.PHONE_NUMBER
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import org.koin.android.viewmodel.ext.android.viewModel

class InputNewPhoneNumberFragment :
    BaseFragmentWithVM<FragmentInputNewPhoneNumberBinding, AuthorizationViewModel>() {

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
    ): FragmentInputNewPhoneNumberBinding {
        return FragmentInputNewPhoneNumberBinding.inflate(inflater)
    }

    override fun init() {
        initUI()
        initListeners()
    }

    private fun initListeners() {
        binding.btnSend.setOnClickListener {
            viewModel.changeOldPhoneNumber(binding.tiPhoneNumber.getPhoneNumber())
        }

        binding.tiPhoneNumber.isPhoneNumberValid.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnSend.setEnabled(R.color.purple_7358A7)
            } else {
                binding.btnSend.setDisabled(R.color.gray_light_CBCBCB)
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.changePhoneNumberFragment, true)
        }
    }

    private fun initUI() {
        binding.inclToolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToCodeConfirmation(phoneNumber: String) {
        val action =
            InputNewPhoneNumberFragmentDirections.actionInputNewPhoneNumberFragmentToCodeConfirmationFragment(
                Authorization.CHANGEPHONENUMBER,
                phoneNumber
            )
        findNavController().navigate(action)
    }
}