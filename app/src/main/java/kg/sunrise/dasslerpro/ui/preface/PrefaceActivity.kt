package kg.sunrise.dasslerpro.ui.preface

import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.activity.BaseActivity
import kg.sunrise.dasslerpro.databinding.ActivityPrefaceBinding

class PrefaceActivity : BaseActivity<ActivityPrefaceBinding>() {

    override val navContainerId: Int = R.id.nav_container
    override val internetConnectionLayout: ConstraintLayout by lazy {
        binding.inclNoInternet.clNoInternet
    }

    override val bindingInflater: (LayoutInflater) -> ActivityPrefaceBinding = { it ->
        ActivityPrefaceBinding.inflate(it)
    }
}