package kg.sunrise.dasslerpro.ui.shared.socialAdapter

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter
import kg.sunrise.dasslerpro.data.models.responses.PartnerSocial
import kg.sunrise.dasslerpro.dto.SocialDto

class SocialAdapter(
    private val onSocialClick: ((String) -> Unit)
) : BaseAdapter<SocialDto, SocialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialViewHolder {
        return SocialViewHolder(parent, R.layout.rv_social_image, onSocialClick)
    }
}

