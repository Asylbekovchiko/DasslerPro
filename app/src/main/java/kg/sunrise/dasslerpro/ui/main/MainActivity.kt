package kg.sunrise.dasslerpro.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.activity.BaseActivity
import kg.sunrise.dasslerpro.databinding.ActivityMainBinding
import kg.sunrise.dasslerpro.ui.auth.AuthActivity
import kg.sunrise.dasslerpro.utils.extensions.gone
import kg.sunrise.dasslerpro.utils.extensions.transitionFade
import kg.sunrise.dasslerpro.utils.extensions.setupWithNavController
import kg.sunrise.dasslerpro.utils.extensions.visible

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val internetConnectionLayout: ConstraintLayout by lazy {
        binding.inclNoInternet.clNoInternet
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = { it ->
        ActivityMainBinding.inflate(it)
    }

    override val navContainerId: Int = R.id.nav_host_container

    private var currentNavController: LiveData<NavController>? = null

    private val navIdsForBottomNavShow = listOf(
        R.id.mainFragment,
        R.id.handbookFragment,
        R.id.infoFragment,
        R.id.profileFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigation = binding.bottomNav

        val navGraphIds = listOf(
            R.navigation.main_nav_graph,
            R.navigation.handbook_nav_graph,
            R.navigation.info_nav_graph,
            R.navigation.profile_nav_graph
        )

        val controller = bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        ) {
            navigateToAuth()
        }

        controller.observe(this) {
            it.addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id in navIdsForBottomNavShow) {
                    bottomNavigation.visible()
                } else {
                    bottomNavigation.gone()
                }
            }
        }

        currentNavController = controller
    }

    override fun onNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }

    private fun navigateToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        transitionFade()
    }
}