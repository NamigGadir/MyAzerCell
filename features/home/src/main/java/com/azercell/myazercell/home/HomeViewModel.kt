package com.azercell.myazercell.home

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.Constants
import com.azercell.myazercell.core.util.SessionManager
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.usecase.auth.RegisterUseCase
import com.azercell.myazercell.domain.usecase.home.GetCardsUseCase
import com.azercell.myazercell.domain.usecase.home.GetCurrentUserInfoUseCase
import com.azercell.myazercell.domain.validators.PhoneNumberValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCurrentUserInfoUseCase: GetCurrentUserInfoUseCase,
    getCardsUseCase: GetCardsUseCase
) : BaseViewModel<HomeContract.HomeState, HomeContract.HomeEffect, HomeContract.HomeEvent>() {

    init {
        getCardsUseCase.invokeRequest(SessionManager.currentToken) {
            postState {
                copy(
                    cardsList = it
                )
            }
        }
        getCurrentUserInfoUseCase.invokeRequest(SessionManager.currentToken) {
            postState {
                copy(
                    userInfo = it
                )
            }
        }
    }

    override fun setInitialState() = HomeContract.HomeState()

    override fun handleEvents(event: HomeContract.HomeEvent) {
        when (event) {
            else -> {}
        }
    }
}

class HomeContract {
    sealed class HomeEvent : UIEvent {

    }

    data class HomeState(
        val cardsList: List<Card>? = null,
        var userInfo: RegisterRequest? = null
    ) : UIState

    sealed class HomeEffect : UIEffect {

    }

}