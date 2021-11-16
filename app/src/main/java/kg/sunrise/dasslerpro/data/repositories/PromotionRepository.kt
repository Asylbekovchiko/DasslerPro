package kg.sunrise.dasslerpro.data.repositories

import androidx.paging.PagingData
import kg.sunrise.dasslerpro.base.repository.BaseRepositoryPaging
import kg.sunrise.dasslerpro.data.api.PromotionApi
import kg.sunrise.dasslerpro.data.models.responses.CouponInfoResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionDetailResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionsResponse
import kg.sunrise.dasslerpro.ui.main.main.paging.PromotionPagingSource
import kg.sunrise.dasslerpro.utils.constants.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PromotionRepository(
    private val promotionApi: PromotionApi
) : BaseRepositoryPaging() {

    suspend fun getPromotions(page: Int): Response<PromotionsResponse>? {
        return makeRequest {
            promotionApi.getPromotions(page)
        }
    }

    suspend fun getPromotionInfo(promotionId: Int): Response<PromotionDetailResponse>? {
        return makeRequest {
            promotionApi.getPromotionInfo(promotionId)
        }
    }

    fun getPromotionsByPage(
        getPromotionsCallback: suspend (Int) -> Response<PromotionsResponse>?
    ): Flow<PagingData<PromotionResponse>> {
        return getDataByPage(
            DEFAULT_PAGE_SIZE,
            PromotionPagingSource() { page ->
                getPromotionsCallback(page)
            }
        )
    }

    suspend fun activateCoupon(promotionID: Int, couponCode: String): Response<CouponInfoResponse>? {
        return makeRequest {
            promotionApi.activateCoupon(promotionID, couponCode)
        }
    }
}

