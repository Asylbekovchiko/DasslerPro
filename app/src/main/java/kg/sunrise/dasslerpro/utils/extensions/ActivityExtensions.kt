package kg.sunrise.dasslerpro.utils.extensions

import android.app.Activity
import android.content.Intent
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.ui.main.MainActivity

fun Activity.transitionFade() {
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}


fun Activity.navigateToMain() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    transitionFade()
}