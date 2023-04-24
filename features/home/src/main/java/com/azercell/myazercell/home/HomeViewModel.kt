package com.azercell.myazercell.home

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.SessionManager
import com.azercell.myazercell.domain.entity.enum.CardCurrency
import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.usecase.home.GetCardsUseCase
import com.azercell.myazercell.domain.usecase.home.GetCurrentUserInfoUseCase
import com.azercell.myazercell.domain.usecase.home.OrderCardUseCase
import com.azercell.myazercell.domain.usecase.home.RemoveCardUseCase
import com.azercell.myazercell.home.order_card.OrderCardContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserInfoUseCase: GetCurrentUserInfoUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val removeCardUseCase: RemoveCardUseCase,
) : BaseViewModel<HomeContract.HomeState, HomeContract.HomeEffect, HomeContract.HomeEvent>() {

    init {
        getCards()
        getUserInfo()
    }

    private fun getUserInfo() {
        getCurrentUserInfoUseCase.invokeRequest(SessionManager.currentToken) {
            postState {
                copy(
                    userInfo = it
                )
            }
        }
    }

    private fun getCards() {
        getCardsUseCase.invokeRequest(SessionManager.currentToken) {
            postState {
                copy(
                    cardsList = it
                )
            }
        }
    }

    override fun setInitialState() = HomeContract.HomeState()

    override fun handleEvents(event: HomeContract.HomeEvent) {
        when (event) {
            is HomeContract.HomeEvent.OnCardDelete -> {
                removeCardUseCase.invokeRequest(
                    RemoveCardUseCase.Params(
                        SessionManager.currentToken,
                        event.cardId
                    )
                ) {
                    postEffect {
                        HomeContract.HomeEffect.OnCardDeleteSuccess
                    }
                }
            }
            HomeContract.HomeEvent.RefreshCards -> {
                getCardsUseCase.invokeRequest(SessionManager.currentToken) {
                    postState {
                        copy(
                            cards2List = it
                        )
                    }
                }
            }
        }
    }
}

class HomeContract {
    sealed class HomeEvent : UIEvent {
        data class OnCardDelete(val cardId: Long) : HomeEvent()
        object RefreshCards : HomeEvent()
    }

    data class HomeState(
        val cardsList: List<Card>? = null,
        val cards2List: List<Card>? = null,
        var userInfo: RegisterRequest? = null
    ) : UIState

    sealed class HomeEffect : UIEffect {
        object OnCardDeleteSuccess : HomeEffect()
    }

}