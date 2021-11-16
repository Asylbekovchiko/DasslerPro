package kg.sunrise.dasslerpro.ui.auth

import kg.sunrise.dasslerpro.App.Companion.context
import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse
import kg.sunrise.dasslerpro.data.repositories.AuthorizationRepository
import kg.sunrise.dasslerpro.utils.constants.CONFIRM_PHONE
import kg.sunrise.dasslerpro.utils.constants.PHONE_NUMBER
import kg.sunrise.dasslerpro.utils.constants.SEND_CONFIRM_CODE
import kg.sunrise.dasslerpro.utils.network.State
import kg.sunrise.dasslerpro.utils.preference.getDeviceToken

class AuthorizationViewModel(
    private val authorizationRepository: AuthorizationRepository
) : BaseViewModel() {

    fun sendAuthorizationPhoneNumber(phoneNumber: String) = getViewModelScope {
        val response = authorizationRepository.sendAuthorizationPhoneNumber(phoneNumber)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState<String>(PHONE_NUMBER)
        }
    }

    fun sendConfirmCode(phoneNumber: String, code: String) = getViewModelScope {
        val response = authorizationRepository.sendConfirmationCode(phoneNumber, code)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun fillUserInfo(token: String, userInfo: UserRequestResponse) = getViewModelScope {
        val response = authorizationRepository.fillUserInfo("Token $token", userInfo)

        _state.value = State.SuccessState.SuccessObjectState(userInfo)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun updateUserInfo(userInfo: UserRequestResponse) = getViewModelScope {
        val response = authorizationRepository.updateUserInfo(userInfo)

        _state.value = State.SuccessState.SuccessObjectState(userInfo)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun getUserInfo() = getViewModelScope {
        val response = authorizationRepository.getUserInfo()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun removeRegistrationId() = getViewModelScope {
        val registrationId = getDeviceToken(context)
        val response = authorizationRepository.removeRegistrationId(registrationId)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.NoItemState
        }
    }

    fun sendConfirmCodeToOldPhone() = getViewModelScope {
        val response = authorizationRepository.sendConfirmCodeToOldPhone()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(SEND_CONFIRM_CODE)
        }
    }

    fun confirmOldPhoneNumber(confirmCode: String) = getViewModelScope {
        val response = authorizationRepository.confirmOldPhoneNumber(confirmCode)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(CONFIRM_PHONE)
        }
    }

    fun changeOldPhoneNumber(phoneNumber: String) = getViewModelScope {
        val response = authorizationRepository.changeOldPhoneNumber(phoneNumber)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(PHONE_NUMBER)
        }
    }

    fun confirmationNewPhoneNumber(confirmationCode: String) = getViewModelScope {
        val response = authorizationRepository.confirmNewPhoneNumber(confirmationCode)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(CONFIRM_PHONE)
        }
    }
}