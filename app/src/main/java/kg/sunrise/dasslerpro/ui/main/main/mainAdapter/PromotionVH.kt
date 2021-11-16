package kg.sunrise.dasslerpro.ui.main.main.mainAdapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse
import kg.sunrise.dasslerpro.ui.customLayouts.views.CrownView
import kg.sunrise.dasslerpro.utils.extensions.gone
import kg.sunrise.dasslerpro.utils.extensions.visible
import kg.sunrise.dasslerpro.utils.parsers.DateTimeParser
import kg.sunrise.dasslerpro.utils.parsers.DateTimePattern

class PromotionVH(
    parent: ViewGroup,
    @LayoutRes id: Int,
    private val commands: PromotionCommands
) : BaseVH<PromotionResponse>(parent, id) {

    private val clMainContent = itemView.findViewById<ConstraintLayout>(R.id.cl_promotion_item)
    private val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
    private val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
    private val clCrowns = itemView.findViewById<ConstraintLayout>(R.id.cl_crowns)
    private val vCrownBronze = itemView.findViewById<CrownView>(R.id.cw_bronze)
    private val vCrownGolden = itemView.findViewById<CrownView>(R.id.cw_golden)
    private val vCrownPlatinum = itemView.findViewById<CrownView>(R.id.cw_platinum)

    override fun bind(item: PromotionResponse) {
        Glide.with(itemView.context)
            .load(item.image)
            .placeholder(R.drawable.ic_placeholder_promotion)
            .into(ivImage)

        clMainContent.setOnClickListener {
            commands.onItemClick(item.promotionID)
        }

        tvTitle.text = item.title
        tvDate.text = DateTimeParser.formatDateTime(item.endDate, DateTimePattern.yyyy_MM_dd_with_dash, DateTimePattern.dd_MM_yyy_with_dots)

        setUpCrowns(item)
    }

    private fun setUpCrowns(promotion: PromotionResponse) {
        promotion.apply {
            if (premiumCount == null && goldCount == null && standardCount == null)  {
                clCrowns.gone()
                return
            }

            clCrowns.visible()

            vCrownBronze.visibility = if (standardCount == null || standardCount == 0) View.GONE else View.VISIBLE
            vCrownBronze.setText(standardCount.toString())

            vCrownGolden.visibility = if (goldCount == null ||  goldCount == 0) View.GONE else View.VISIBLE
            vCrownGolden.setText(goldCount.toString())

            vCrownPlatinum.visibility = if (premiumCount == null || premiumCount == 0) View.GONE else View.VISIBLE
            vCrownPlatinum.setText(premiumCount.toString())
        }

        promotion.couponInfo?.let {
            setCrownClick(it.title, it.description)
        }
    }

    private fun setCrownClick(title: String, description: String) {
        clCrowns.setOnClickListener {
            commands.showBottomSheet(title, description)
        }
    }
}