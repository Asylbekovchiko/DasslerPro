package kg.sunrise.dasslerpro.utils.extensions

import android.content.Intent
import android.os.Build
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.activity.BaseActivity
import kg.sunrise.dasslerpro.ui.main.MainActivity

/**
 * Set status bar color only if Build Version >= 23
 */
fun Fragment.setStatusBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requireActivity().window.statusBarColor = requireContext().getColorCompat(color)
    }
}

fun Fragment.navigateToMain() {
    val intent = Intent(requireContext(), MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    requireActivity().transitionFade()
}

fun FragmentActivity.transitionFade() {
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun Fragment.setNoInternetLayoutVisibility(isHasConnection: Boolean) {
    (requireActivity() as? BaseActivity<*>)?.setInternetConnectionVisibility(isHasConnection)
}

fun Fragment.bindRefreshBtn(btnClick: () -> Unit) {
    val refreshBtn =
        (requireActivity() as? BaseActivity<*>)?.findViewById<Button>(R.id.btn_refresh_connection)
    refreshBtn?.setOnClickListener { btnClick.invoke() }
}