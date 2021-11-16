package kg.sunrise.dasslerpro.ui.main.info

import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel
import kg.sunrise.dasslerpro.data.models.responses.AboutUsInfoResponse
import kg.sunrise.dasslerpro.data.repositories.ApplicationInfoRepository
import kg.sunrise.dasslerpro.data.repositories.InfoRepository
import kg.sunrise.dasslerpro.utils.network.State

class InfoViewModel(
    private val infoRepository: InfoRepository
) : BaseViewModel() {

    fun getInfoAboutUs()  = getViewModelScope {
        val response = infoRepository.getInfoAboutUs()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun getPrivacyInfo() = getViewModelScope {
        val response = infoRepository.getPrivacyInfo()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    var aboutUsInfo: AboutUsInfoResponse? = null
}