package com.azercell.myazercell.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.extensions.setupCardTransformer
import com.azercell.myazercell.home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeContract.HomeState, HomeContract.HomeEffect, HomeContract.HomeEvent>() {

    private val cardAdapter: MyCardsPageAdapter by lazy { MyCardsPageAdapter() }

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun getViewModelClass() = HomeViewModel::class.java

    override val onViewInit: FragmentHomeBinding.() -> Unit = {
        cardsPager.adapter = cardAdapter
        cardsPager.setupCardTransformer()
        orderNewCardButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOrderCardFragment())
        }
    }

    override fun onStateUpdate(state: HomeContract.HomeState) {
        state.cardsList?.let {
            cardAdapter.submitList(it)
        }
        binding.userName.text = state.userInfo?.name ?: "Blaaaaaa"
    }

}
