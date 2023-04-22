package com.azercell.myazercell.presentation

import android.view.LayoutInflater
import com.azercell.myazercell.core.base.BaseActivity
import com.azercell.myazercell.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding, MainContract.MainState, MainContract.MainEffect, MainContract.MainEvent>() {

    override val onViewBinding: () -> ActivityMainBinding
        get() = { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    override fun getViewModelClass() = MainViewModel::class.java

    override val onViewInit: ActivityMainBinding.() -> Unit = {

    }

}