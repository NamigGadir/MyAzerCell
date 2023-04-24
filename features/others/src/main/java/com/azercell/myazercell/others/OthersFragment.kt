package com.azercell.myazercell.others

import android.view.LayoutInflater
import android.view.ViewGroup
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.base.SessionHandler
import com.azercell.myazercell.others.databinding.FragmentOthersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OthersFragment : BaseFragment<OthersViewModel, FragmentOthersBinding, OthersContract.OthersState, OthersContract.OthersEffect, OthersContract.OthersEvent>() {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOthersBinding
        get() = FragmentOthersBinding::inflate

    override fun getViewModelClass() = OthersViewModel::class.java

    override val onViewInit: FragmentOthersBinding.() -> Unit = {
        logoutLayout.setOnClickListener {
            postEvent {
                OthersContract.OthersEvent.Logout
            }
        }
    }

    override fun onEffectUpdate(effect: OthersContract.OthersEffect) {
        when (effect) {
            OthersContract.OthersEffect.OnLogout -> {
                (requireActivity() as SessionHandler).onLogout()
            }
        }
    }

}