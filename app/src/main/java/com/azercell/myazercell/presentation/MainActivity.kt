package com.azercell.myazercell.presentation

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.azercell.myazercell.R
import com.azercell.myazercell.core.base.BaseActivity
import com.azercell.myazercell.core.base.SessionHandler
import com.azercell.myazercell.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding, MainContract.MainState, MainContract.MainEffect, MainContract.MainEvent>(), SessionHandler {

    override val onViewBinding: () -> ActivityMainBinding
        get() = { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    override fun getViewModelClass() = MainViewModel::class.java

    override val onViewInit: ActivityMainBinding.() -> Unit = {

    }

    override fun onRegister(authToken: String) {
        val navBuilder = NavOptions.Builder()
        val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.main_nav_graph, false).build()
        findNavController(R.id.main_nav_fragment).navigate(com.azercell.myazercell.home.R.id.home_nav_graph, bundleOf(AUTH_TOKEN_BUNDLE to authToken), navOptions)
    }

    companion object {
        const val AUTH_TOKEN_BUNDLE = "AUTH_TOKEN_BUNDLE"
    }

}