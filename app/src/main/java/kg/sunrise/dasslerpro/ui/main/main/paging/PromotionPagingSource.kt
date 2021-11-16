package kg.sunrise.dasslerpro.ui.main.main.paging

import kg.sunrise.dasslerpro.base.paging.BasePagingSource
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse
import kg.sunrise.dasslerpro.data.models.responses.PromotionsResponse
import retrofit2.Response

class PromotionPagingSource(
    getPromotionsCallback: suspend (Int) -> Response<PromotionsResponse>?
) : BasePagingSource<PromotionsResponse, PromotionResponse>(getPromotionsCallback) {

    override fun getNextKey(responseBody: PromotionsResponse): Int? {
        return responseBody.next
    }

    override fun getResponseItem(responseBody: PromotionsResponse): ArrayList<PromotionResponse> {
        responseBody.promotions.forEach {
            it.couponInfo = responseBody.couponInfo
        }

        return responseBody.promotions
    }
}