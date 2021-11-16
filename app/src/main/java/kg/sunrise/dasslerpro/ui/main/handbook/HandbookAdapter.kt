package kg.sunrise.dasslerpro.ui.main.handbook

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter
import kg.sunrise.dasslerpro.data.models.responses.HandbookQuestion

class HandbookAdapter : BaseAdapter<HandbookQuestion, HandbookVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HandbookVH = HandbookVH(parent, R.layout.item_handbook)

}