package kg.sunrise.dasslerpro.ui.main.info.phoneNumberAdapter

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter

class PhoneNumberAdapter : BaseAdapter<String, PhoneNumberVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneNumberVH {
        return PhoneNumberVH(parent, R.layout.rv_phonenumber_item)
    }
}

