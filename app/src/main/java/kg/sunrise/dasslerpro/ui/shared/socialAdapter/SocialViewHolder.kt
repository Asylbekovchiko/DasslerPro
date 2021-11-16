package kg.sunrise.dasslerpro.ui.shared.socialAdapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH
import kg.sunrise.dasslerpro.data.models.responses.PartnerSocial
import kg.sunrise.dasslerpro.dto.SocialDto

class SocialViewHolder(
    parent: ViewGroup,
    @LayoutRes id: Int,
    private val onSocialClick: (String) -> Unit
) : BaseVH<SocialDto>(parent, id) {

    private val image = itemView.findViewById<ImageView>(R.id.iv_image)

    override fun bind(item: SocialDto) {
        image.setOnClickListener {
            onSocialClick(item.link)
        }

        Glide.with(itemView)
            .load(item.image)
            .into(image)
    }
}