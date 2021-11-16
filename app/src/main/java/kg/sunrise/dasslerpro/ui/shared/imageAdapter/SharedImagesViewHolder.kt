package kg.sunrise.dasslerpro.ui.shared.imageAdapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.ortiz.touchview.TouchImageView
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH


class SharedImagesViewHolder(
    val parent: ViewGroup,
    @LayoutRes id: Int
) : BaseVH<String>(parent, id) {

    private val image = itemView.findViewById<TouchImageView>(R.id.iv_image)

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(item: String) {

        image.resetZoom()

        image.setOnTouchListener { view, event ->
            val result = if (image.isZoomed) {
                parent.requestDisallowInterceptTouchEvent(true)
                false
            } else {
                parent.requestDisallowInterceptTouchEvent(false)
                true
            }
            result
        }

        Glide.with(itemView)
            .load(item)
            .into(image)
    }
}

