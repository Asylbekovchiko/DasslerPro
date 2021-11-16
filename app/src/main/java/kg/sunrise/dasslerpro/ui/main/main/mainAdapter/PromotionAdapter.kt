package kg.sunrise.dasslerpro.ui.main.main.mainAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter
import kg.sunrise.dasslerpro.base.paging.BasePagingAdapter
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse

class PromotionAdapter(
    private val commands: PromotionCommands
) : BasePagingAdapter<PromotionResponse, PromotionVH>(PROMOTION_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionVH {
        return PromotionVH(parent, R.layout.rv_promotion_item, commands)
    }

    companion object {
        private val PROMOTION_COMPARATOR = object : DiffUtil.ItemCallback<PromotionResponse>() {

            override fun areItemsTheSame(oldItem: PromotionResponse, newItem: PromotionResponse): Boolean {
                return oldItem.promotionID == newItem.promotionID
            }

            override fun areContentsTheSame(oldItem: PromotionResponse, newItem: PromotionResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}


