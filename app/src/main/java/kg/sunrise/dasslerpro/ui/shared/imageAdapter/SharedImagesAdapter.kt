package kg.sunrise.dasslerpro.ui.shared.imageAdapter

import android.view.ViewGroup
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseAdapter

class SharedImagesAdapter(private val onImageClick: ((Int) -> Unit)? = null) : BaseAdapter<String, SharedImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedImagesViewHolder {


        return SharedImagesViewHolder(parent, R.layout.rv_shared_image_item)
    }

    override fun onBindViewHolder(holder: SharedImagesViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            onImageClick?.invoke(position)
        }
    }

    fun getImages() = items
}

