package kg.sunrise.dasslerpro.ui.preface

import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel
import kg.sunrise.dasslerpro.data.repositories.ApplicationInfoRepository
import kg.sunrise.dasslerpro.utils.network.State

class PrefaceViewModel(
    private val applicationInfoRepository: ApplicationInfoRepository
) : BaseViewModel() {

    var isDelayEnd = false
    var isVersionValid = false

    fun getVersion() = getViewModelScope {
        val response = applicationInfoRepository.getVersion()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }
}