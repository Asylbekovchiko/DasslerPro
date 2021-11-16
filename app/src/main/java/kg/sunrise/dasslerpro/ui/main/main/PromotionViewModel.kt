package kg.sunrise.dasslerpro.ui.main.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.sunrise.dasslerpro.base.viewModel.BaseViewModelPaging
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionsResponse
import kg.sunrise.dasslerpro.data.repositories.PromotionRepository
import kg.sunrise.dasslerpro.utils.network.State
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PromotionViewModel(
    private val promotionRepository: PromotionRepository
) : BaseViewModelPaging<PromotionResponse>() {

    override var cashedData: Flow<PagingData<PromotionResponse>>? = null

    fun getPromotionsPaging(): Flow<PagingData<PromotionResponse>> {
        val lastResult = cashedData

        if (lastResult != null)
            return lastResult

        val result = promotionRepository.getPromotionsByPage { page ->
            getPromotions(page)
        }.cachedIn(viewModelScope)

        cashedData = result

        return result
    }

    private suspend fun getPromotions(
        page: Int,
    ): Response<PromotionsResponse>? {
        return getResponsePaging() {
            val response = promotionRepository.getPromotions(page)

            if (!response.hasBody()) return@getResponsePaging null

            if (response!!.isSuccess()) {
                _state.value = State.SuccessState.NoItemState
            }

            return@getResponsePaging response
        }
    }
}