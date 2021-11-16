package kg.sunrise.dasslerpro.ui.preface.slider.sliderAdapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH
import kg.sunrise.dasslerpro.dto.SliderInfoDto

class SliderVH(parent: ViewGroup, @LayoutRes id: Int) : BaseVH<SliderInfoDto>(parent, id) {

    private val ivImage = itemView.findViewById<ImageView>(R.id.iv_slider_item)

    override fun bind(item: SliderInfoDto) {
        ivImage.setImageResource(item.id)
    }
}

