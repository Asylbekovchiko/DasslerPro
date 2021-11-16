package kg.sunrise.dasslerpro.ui.main.main.promotionDetail

import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel
import kg.sunrise.dasslerpro.data.repositories.PromotionRepository
import kg.sunrise.dasslerpro.utils.network.State

class PromotionDetailViewModel(
    private val promotionRepository: PromotionRepository
) : BaseViewModel() {

    fun getPromotionInfo(promotionId: Int) = getViewModelScope {
        val response = promotionRepository.getPromotionInfo(promotionId)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }

    fun activateCoupon(promotionID: Int, couponCode: String) = getViewModelScope {
        val response = promotionRepository.activateCoupon(promotionID, couponCode)

        if (!response.hasBody()) return@getViewModelScope

        if (response!!.isSuccess()) {
            _state.value = State.SuccessState.SuccessObjectState(response.body())
        }
    }
}