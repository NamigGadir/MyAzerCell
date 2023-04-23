package com.azercell.myazercell.home.order_card

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.SessionManager
import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.usecase.home.GetCurrentUserInfoUseCase
import com.azercell.myazercell.domain.usecase.home.OrderCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderCardViewModel @Inject constructor(
    getCurrentUserInfoUseCase: GetCurrentUserInfoUseCase,
    private val orderCardUseCase: OrderCardUseCase
) : BaseViewModel<OrderCardContract.OrderCardState, OrderCardContract.OrderCardEffect, OrderCardContract.OrderCardEvent>() {

    companion object {
        private const val CUR_AZN = "AZN"
        private const val CUR_EUR = "EUR"
        private const val CUR_USD = "USD"
    }

    init {
        getCurrentUserInfoUseCase.invokeRequest(SessionManager.currentToken) {
            val cartTypes = CardNetwork.values().map { it.name }
            postState {
                copy(
                    userInfo = it,
                    cardNetworks = cartTypes,
                    cardCurrencies = listOf(CUR_AZN, CUR_USD, CUR_EUR)
                )
            }
        }
    }


    override fun setInitialState() = OrderCardContract.OrderCardState()

    override fun handleEvents(event: OrderCardContract.OrderCardEvent) {
        when (event) {
            is OrderCardContract.OrderCardEvent.SelectedCardType -> {
                postEffect {
                    OrderCardContract.OrderCardEffect.OnCardTypeSelected
                }
            }
            is OrderCardContract.OrderCardEvent.StartOrderCard -> startOrderCard(event)
        }
    }

    private fun startOrderCard(event: OrderCardContract.OrderCardEvent.StartOrderCard) {
        state.value.cardNetworks?.let {
            val cardNetwork = CardNetwork.valueOf(it[event.selectedCardNetworkPosition])
            val cardHolderName = event.cardHolderName
            val cardCurrency = event.selectedCardCurrency
            orderCardUseCase.invokeRequest(
                OrderCardUseCase.Params(
                    SessionManager.currentToken, CardOrderRequest(
                        cardHolderName = cardHolderName,
                        cardType = cardNetwork,
                        cardCurrency = cardCurrency
                    )
                )
            ) {
                postEffect {
                    OrderCardContract.OrderCardEffect.OnCardAdded
                }
            }
        }
    }
}

class OrderCardContract {
    sealed class OrderCardEvent : UIEvent {
        data class SelectedCardType(val position: Int) : OrderCardEvent()
        data class StartOrderCard(
            val selectedCardNetworkPosition: Int,
            val selectedCardCurrency: String,
            val cardHolderName: String
        ) : OrderCardEvent()
    }

    data class OrderCardState(
        val cardNetworks: List<String>? = null,
        val cardCurrencies: List<String>? = null,
        val userInfo: RegisterRequest? = null
    ) : UIState

    sealed class OrderCardEffect : UIEffect {
        object OnCardTypeSelected : OrderCardEffect()
        object OnCardAdded : OrderCardEffect()
    }
}