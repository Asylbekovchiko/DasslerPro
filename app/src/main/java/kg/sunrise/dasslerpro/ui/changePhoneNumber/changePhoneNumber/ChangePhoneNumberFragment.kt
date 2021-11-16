package kg.sunrise.dasslerpro.ui.changePhoneNumber.changePhoneNumber

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
import kg.sunrise.dasslerpro.databinding.FragmentChangePhoneNumberBinding
import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.utils.constants.CONFIRMATION_CODE_LENGTH
import kg.sunrise.dasslerpro.utils.constants.CONFIRM_PHONE
import kg.sunrise.dasslerpro.utils.constants.PHONE_NUMBER
import kg.sunrise.dasslerpro.utils.constants.SECONDS_IN_MINUTES
import kg.sunrise.dasslerpro.utils.extensions.setDisabled
import kg.sunrise.dasslerpro.utils.extensions.setEnabled
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class ChangePhoneNumberFragment :
    BaseFragmentWithVM<FragmentChangePhoneNumberBinding, AuthorizationViewModel>() {

    private val navArgs by navArgs<ChangePhoneNumberFragmentArgs>()

    override val viewModel: AuthorizationViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    private var timer: CountDownTimer? = null

    override fun makeRequests() {
        viewModel.checkInternetConnection()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is String -> {
                if (data == PHONE_NUMBER) {
                    initTimer()
                } else if (data == CONFIRM_PHONE) {
                    navigateToInputNumber()
                }
            }
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangePhoneNumberBinding {
        return FragmentChangePhoneNumberBinding.inflate(inflater)
    }

    override fun init() {
        initUI()
        initListeners()
        initTimer()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.confirmOldPhoneNumber(binding.tiCodeConfirmation.getInputText())
        }

        binding.inclToolbar.ivBack.setOnClickListener {
            navigateBack()
        }
        binding.btnSendAgain.setOnClickListener {
            viewModel.sendConfirmCodeToOldPhone()
            initTimer()
            constraintEnd(false)
            binding.btnSendAgain.setDisabled(R.color.gray_light_CBCBCB)
        }

        binding.tiCodeConfirmation.addTextChangedListener {
            val code = binding.tiCodeConfirmation.getInputText()

            if (code.length == CONFIRMATION_CODE_LENGTH) {
                binding.btnNext.setEnabled(R.color.purple_7358A7)
            } else {
                binding.btnNext.setDisabled(R.color.gray_light_CBCBCB)
            }
        }
    }

    private fun initUI() {
        binding.tvDesc.text = getString(R.string.Confirmation_code_description, navArgs.phoneNumber)
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

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun navigateToInputNumber() {
        val action =
            ChangePhoneNumberFragmentDirections.actionChangePhoneNumberFragmentToInputNewPhoneNumberFragment()
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