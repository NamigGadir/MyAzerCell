package com.azercell.myazercell.presentation

import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.azercell.myazercell.R
import com.azercell.myazercell.core.base.BaseActivity
import com.azercell.myazercell.core.base.SessionHandler
import com.azercell.myazercell.core.extensions.gone
import com.azercell.myazercell.core.extensions.show
import com.azercell.myazercell.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding, MainContract.MainState, MainContract.MainEffect, MainContract.MainEvent>(), SessionHandler {

    private lateinit var navController: NavController
    private val hiddenBottomNavigationViews by lazy {
        setOf(
            com.azercell.myazercell.auth.R.id.registrationFragment
        )
    }

    private val config: AppBarConfiguration by lazy {
        AppBarConfiguration.Builder(
            setOf(
                com.azercell.myazercell.home.R.id.homeFragment,
                com.azercell.myazercell.transfers.R.id.transfersFragment,
                com.azercell.myazercell.others.R.id.othersFragment,
            )
        ).build()
    }

    override val onViewBinding: () -> ActivityMainBinding
        get() = { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    override fun getViewModelClass() = MainViewModel::class.java

    override val onViewInit: ActivityMainBinding.() -> Unit = {
        setGraph()
    }

    private fun setGraph() {
        with(binding) {
            val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment? ?: return
            navController = navHost.navController
            val graph = navHost.navController
                .navInflater.inflate(R.navigation.main_nav_graph)
            navController.graph = graph
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                if (destination.id !in hiddenBottomNavigationViews)
                    bottomNavigationMenu.show()
                else
                    bottomNavigationMenu.gone()
            }
            NavigationUI.setupWithNavController(bottomNavigationMenu, navController)
        }
    }

    override fun onRegister(authToken: String) {
        val navBuilder = NavOptions.Builder()
        val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.main_nav_graph, false).build()
        findNavController(R.id.main_nav_fragment).navigate(com.azercell.myazercell.home.R.id.home_nav_graph, bundleOf(AUTH_TOKEN_BUNDLE to authToken), navOptions)
    }

    override fun onToolbarChange(toolbar: Toolbar) {
        NavigationUI.setupWithNavController(toolbar, navController, config)
    }

    companion object {
        const val AUTH_TOKEN_BUNDLE = "AUTH_TOKEN_BUNDLE"
    }

}