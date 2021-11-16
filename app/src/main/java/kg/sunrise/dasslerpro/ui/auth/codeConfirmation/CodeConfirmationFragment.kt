package kg.sunrise.dasslerpro.ui.auth.codeConfirmation

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.ConfirmResponse
import kg.sunrise.dasslerpro.databinding.FragmentCodeConfirmationBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.ui.customLayouts.alertDialogs.SuccessDialogFragment
import kg.sunrise.dasslerpro.utils.constants.*
import kg.sunrise.dasslerpro.utils.extensions.navigateToMain
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import kg.sunrise.dasslerpro.utils.preference.setToken
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CodeConfirmationFragment :
    BaseFragmentWithVM<FragmentCodeConfirmationBinding, AuthorizationViewModel>() {

    override val viewModel: AuthorizationViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    private val navArgs by navArgs<CodeConfirmationFragmentArgs>()
    private var timer: CountDownTimer? = null

    override fun makeRequests() {
        viewModel.checkInternetConnection()
    }

    override fun findTypeOfObject(data: Any?) {
        // make handle for requests
        when (data) {
            is String -> {
                if (data == PHONE_NUMBER) {
                    initTimer()
                } else if (data == CONFIRM_PHONE) {
                    handleChangePhoneNumberConfirmation()
                }
            }
            is ConfirmResponse -> {
                when (navArgs.authorization) {
                    Authorization.REGISTRATION -> {
                        handleConfirmation(data)
                    }
                }
            }
        }
    }

    private fun handleChangePhoneNumberConfirmation() {
        SuccessDialogFragment(R.drawable.ic_check_circle, getString(R.string.Success_number_change), null) {
            findNavController().navigate(R.id.action_global_to_profileFragment)
        }.apply {
            isCancelable = false
        }.show(parentFragmentManager, null)
    }

    private fun handleConfirmation(confirmation: ConfirmResponse) {
        if (confirmation.isProfileCompleted) {
            setToken(requireContext(), confirmation.token)
            navigateToMain()
        } else {
            navigateToRegistration(confirmation.token)
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeConfirmationBinding {
        return FragmentCodeConfirmationBinding.inflate(inflater)
    }

    override fun init() {
        initUI()
        initListeners()
        initTimer()
    }

    private fun initUI() {
        binding.tvDesc.text = getString(R.string.Confirmation_code_description_with_number, navArgs.phoneNumber)
    }

    private fun initTimer() {
        timer = setUpTimer(60000)
        timer?.start()
    }

    private fun setUpTimer(milliseconds: Long): CountDownTimer {
        return object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

                val minutes = secondsUntilFinished / SECONDS_IN_MINUTES
                val seconds = secondsUntilFinished % SECONDS_IN_MINUTES

                binding.tvCountDown.text = resources.getString(R.string.timer, minutes, seconds)
            }

            override fun onFinish() {
                binding.btnSendAgain.setEnabled(R.color.blue_7151F0)
                constraintEnd(true)
                binding.tvCountDown.text = ""
            }
        }
    }

    private fun initListeners() {
        binding.btnSendAgain.setOnClickListener {
            when (navArgs.authorization) {
                Authorization.REGISTRATION -> {
                    viewModel.sendAuthorizationPhoneNumber(navArgs.phoneNumber)
                }
                Authorization.CHANGEPHONENUMBER -> {
                    viewModel.changeOldPhoneNumber(navArgs.phoneNumber)
                }
            }

            initTimer()
            constraintEnd(false)
            binding.btnSendAgain.setDisabled(R.color.gray_light_CBCBCB)
        }

        binding.btnSend.setOnClickListener {
            when (navArgs.authorization) {
                Authorization.REGISTRATION -> {
                    viewModel.sendConfirmCode(navArgs.phoneNumber, binding.tiCodeConfirmation.getInputText())
                }
                Authorization.CHANGEPHONENUMBER -> {
                    viewModel.confirmationNewPhoneNumber(binding.tiCodeConfirmation.getInputText())
                }
            }
        }

        binding.tiCodeConfirmation.addTextChangedListener {
            val code = binding.tiCodeConfirmation.getInputText()

            if (code.length == CONFIRMATION_CODE_LENGTH) {
                binding.btnSend.setEnabled(R.color.purple_7358A7)
            } else {
                binding.btnSend.setDisabled(R.color.gray_light_CBCBCB)
            }
        }

        binding.inclToolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvDesc.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToRegistration(token: String) {
        val action =
            CodeConfirmationFragmentDirections.actionCodeConfirmationFragmentToRegistrationFragment(
                navArgs.phoneNumber,
                token
            )
        findNavController().navigate(action)
    }

    private fun constraintEnd(isConstraintEnd: Boolean) {
        val constraintLayout = binding.clParent
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        if (isConstraintEnd) {
            constraintSet.connect(R.id.tv_send_again, ConstraintSet.END, R.id.btn_send_again, ConstraintSet.END)
        } else {
            constraintSet.clear(R.id.tv_send_again, ConstraintSet.END)
        }

        constraintSet.applyTo(constraintLayout)
    }

    override fun onDestroyView() {
        timer?.cancel()

        super.onDestroyView()
    }
}