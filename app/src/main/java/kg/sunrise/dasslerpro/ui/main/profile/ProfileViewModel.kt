package kg.sunrise.dasslerpro.ui.main.profile

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.sunrise.dasslerpro.data.models.responses.UserInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.UserRequestResponse

class ProfileViewModel : ViewModel() {

    var cashedUserInfo: UserInfoResponse? = null

    // required field
    val userName = MutableLiveData<String>()
    // required field
    val lastName = MutableLiveData<String>()
    val patronymicName = MutableLiveData<String>()

    val isPrivacyCheck = MutableLiveData<Boolean>(false)

    var isPrivacyNeeded = true

    val isValid = MediatorLiveData<Boolean>().apply {
        addSource(userName) {
            value = checkRequiredFields()
        }
        addSource(lastName) {
            value = checkRequiredFields()
        }
        addSource(isPrivacyCheck) {
            value = checkRequiredFields()
        }
    }

    private fun checkRequiredFields(): Boolean {
        return (!userName.value.isNullOrBlank()
                && !lastName.value.isNullOrBlank()
                && if (isPrivacyNeeded) isPrivacyCheck.value == true else true)
    }

    fun getUserModel(): UserRequestResponse {
         return if (checkRequiredFields()) {
             UserRequestResponse(
                userName.value!!,
                lastName.value!!,
                patronymicName.value
            )
        } else {
            throw Throwable("1731 user required field not initialized")
        }
    }
}