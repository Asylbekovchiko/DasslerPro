package kg.sunrise.dasslerpro.ui.preface.slider.sliderAdapter

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter
import kg.sunrise.dasslerpro.dto.SliderInfoDto

class SliderAdapter : BaseAdapter<SliderInfoDto, SliderVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderVH {
        return SliderVH(parent, R.layout.rv_slider_item)
    }
}