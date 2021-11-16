package kg.sunrise.dasslerpro.ui.auth

import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.activity.BaseActivity
import kg.sunrise.dasslerpro.databinding.ActivityAuthBinding
import kg.sunrise.dasslerpro.utils.extensions.transitionFade

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override val navContainerId: Int = R.id.nav_container
    override val internetConnectionLayout: ConstraintLayout by lazy {
        binding.inclNoInternet.clNoInternet
    }

    override val bindingInflater: (LayoutInflater) -> ActivityAuthBinding = { it ->
        ActivityAuthBinding.inflate(it)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        transitionFade()
    }
}