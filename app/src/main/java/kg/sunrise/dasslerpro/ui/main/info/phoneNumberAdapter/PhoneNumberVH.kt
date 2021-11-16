package kg.sunrise.dasslerpro.ui.main.info.phoneNumberAdapter

import android.content.Intent
import android.net.Uri
import android.text.TextUtils.replace
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.adapter.BaseVH

class PhoneNumberVH(parent: ViewGroup, id: Int) : BaseVH<String>(parent, id) {

    private val tvPhone = itemView.findViewById<TextView>(R.id.tv_phone)
    private val ivImage = itemView.findViewById<ImageView>(R.id.iv_tel)

    override fun bind(item: String) {
        tvPhone.text = item

        ivImage.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${item.replace("/", "")}")
            }
            startActivity(itemView.context, phoneIntent, null)
        }
    }
}