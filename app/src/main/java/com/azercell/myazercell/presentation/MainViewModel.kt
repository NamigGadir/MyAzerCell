package com.azercell.myazercell.presentation

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainContract.MainState, MainContract.MainEffect, MainContract.MainEvent>() {

    override fun setInitialState() = MainContract.MainState(false)

    override fun handleEvents(event: MainContract.MainEvent) {

    }
}

class MainContract {

    sealed class MainEvent : UIEvent
    class MainState(
        val isLoading: Boolean,
    ) : UIState

    sealed class MainEffect : UIEffect
}
