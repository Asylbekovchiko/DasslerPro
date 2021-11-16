package kg.sunrise.dasslerpro.utils.extensions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

fun Fragment.navigateToSocial(link: String) {
    val uri = Uri.parse(link)
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}


