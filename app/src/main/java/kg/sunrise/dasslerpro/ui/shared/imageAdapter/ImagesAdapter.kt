package kg.sunrise.dasslerpro.ui.shared.imageAdapter

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter

class ImagesAdapter(
    private val ratio: ImageRatio,
    private val onImageClick: ((Int) -> Unit)? = null
) : BaseAdapter<String, ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(ratio, parent, R.layout.rv_image_item)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            onImageClick?.invoke(position)
        }
    }

    fun getImages() = items
}