package kg.sunrise.dasslerpro.ui.shared.imageAdapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH

class ImagesViewHolder(
    private val ratio: ImageRatio,
    parent: ViewGroup,
    @LayoutRes id: Int
) : BaseVH<String>(parent, id) {

    private val image = itemView.findViewById<ShapeableImageView>(R.id.iv_image)

    override fun bind(item: String) {
        Glide.with(itemView)
            .load(item)
            .error(R.drawable.ic_placeholder_promotion)
            .into(image)

        image.updateLayoutParams<ConstraintLayout.LayoutParams> {
            dimensionRatio = ratio.ratio
        }
    }
}