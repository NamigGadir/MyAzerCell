package com.azercell.myazercell.home.remove_card

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
import com.azercell.myazercell.domain.usecase.home.GetCardByIdUseCase
import com.azercell.myazercell.domain.usecase.home.GetCurrentUserInfoUseCase
import com.azercell.myazercell.domain.usecase.home.OrderCardUseCase
import com.azercell.myazercell.domain.usecase.home.RemoveCardUseCase
import com.azercell.myazercell.home.HomeContract
import com.azercell.myazercell.home.order_card.OrderCardContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveCardViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val removeCardUseCase: RemoveCardUseCase
) : BaseViewModel<RemoveCardContract.RemoveCardState, RemoveCardContract.RemoveCardEffect, RemoveCardContract.RemoveCardEvent>() {


    override fun setInitialState() = RemoveCardContract.RemoveCardState()

    override fun handleEvents(event: RemoveCardContract.RemoveCardEvent) {
        when (event) {
            is RemoveCardContract.RemoveCardEvent.OnCardDelete -> {
                removeCardUseCase.invokeRequest(
                    RemoveCardUseCase.Params(
                        SessionManager.currentToken,
                        event.cardId
                    )
                ) {
                    postEffect {
                        RemoveCardContract.RemoveCardEffect.OnCardRemoved
                    }
                }
            }
            is RemoveCardContract.RemoveCardEvent.LoadCardInfo -> loadCardInfo(event.cardId)
        }
    }

    private fun loadCardInfo(cardId: Long) {
        getCardByIdUseCase.invokeRequest(
            GetCardByIdUseCase.Params(
                SessionManager.currentToken, cardId
            )
        ) {
            postState {
                copy(userCard = it)
            }
        }
    }
}

class RemoveCardContract {
    sealed class RemoveCardEvent : UIEvent {
        data class LoadCardInfo(val cardId: Long) : RemoveCardEvent()
        data class OnCardDelete(val cardId: Long) : RemoveCardEvent()
    }

    data class RemoveCardState(
        val userCard: Card? = null
    ) : UIState

    sealed class RemoveCardEffect : UIEffect {
        object OnCardRemoved : RemoveCardEffect()
    }
}