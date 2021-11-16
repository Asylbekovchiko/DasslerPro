package kg.sunrise.dasslerpro.ui.main.handbook

import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel
import kg.sunrise.dasslerpro.data.models.responses.HandbookResponse
import kg.sunrise.dasslerpro.data.repositories.InfoRepository
import kg.sunrise.dasslerpro.utils.network.State

class HandbookViewModel(
    private val repo: InfoRepository
) : BaseViewModel() {

    fun getHandbooks() = getViewModelScope {
        val response = repo.getHandbooks()

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess())
            _state.value =
                State.SuccessState.SuccessObjectState(response.body())
    }

    var handbook: HandbookResponse? = null
}