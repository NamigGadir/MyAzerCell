package com.azercell.myazercell.others

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.SessionManager
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest
import com.azercell.myazercell.domain.usecase.auth.LogoutUseCase
import com.azercell.myazercell.domain.usecase.home.GetCardsUseCase
import com.azercell.myazercell.domain.usecase.transfers.MakeTransfersUseCase
import com.azercell.myazercell.domain.validators.AmountValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OthersViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<OthersContract.OthersState, OthersContract.OthersEffect, OthersContract.OthersEvent>() {

    override fun setInitialState() = OthersContract.OthersState()

    override fun handleEvents(event: OthersContract.OthersEvent) {
        when (event) {
            OthersContract.OthersEvent.Logout -> {
                logoutUseCase.invokeRequest(SessionManager.currentToken) {
                    postEffect {
                        OthersContract.OthersEffect.OnLogout
                    }
                }
            }
        }
    }
}

class OthersContract {

    sealed class OthersEvent : UIEvent {
        object Logout : OthersEvent()
    }

    class OthersState() : UIState

    sealed class OthersEffect : UIEffect {
        object OnLogout : OthersEffect()
    }
}