package com.azercell.myazercell.transfers

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.SessionManager
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest
import com.azercell.myazercell.domain.usecase.home.GetCardsUseCase
import com.azercell.myazercell.domain.usecase.transfers.MakeTransfersUseCase
import com.azercell.myazercell.domain.validators.AmountValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransfersViewModel @Inject constructor(
    getCardsUseCase: GetCardsUseCase,
    private val amountValidator: AmountValidator,
    private val makeTransfersUseCase: MakeTransfersUseCase
) : BaseViewModel<TransfersContract.TransfersState, TransfersContract.TransfersEffect, TransfersContract.TransfersEvent>() {

    init {
        getCardsUseCase.invokeRequest(SessionManager.currentToken) {
            postState {
                copy(
                    cardsList = it
                )
            }
        }
    }

    override fun setInitialState() = TransfersContract.TransfersState()

    override fun handleEvents(event: TransfersContract.TransfersEvent) {
        when (event) {
            is TransfersContract.TransfersEvent.OnCardSelect -> {
                if (event.fromCard is Card && event.toCard is Card) {
                    postEffect {
                        TransfersContract.TransfersEffect.IsUiValid(
                            event.fromCard.id != event.toCard.id
                                    && amountValidator.isValid(event.amount)
                                    && event.amount.toDouble() > 0
                                    && event.amount.toDouble() < event.fromCard.cardBalance
                        )
                    }
                }
            }
            is TransfersContract.TransfersEvent.StartTransfer -> {
                if (event.fromCard is Card && event.toCard is Card && event.amount.toDouble() > 0
                    && event.amount.toDouble() < event.fromCard.cardBalance
                ) {
                    makeTransfersUseCase.invokeRequest(
                        MakeTransfersUseCase.Params(
                            SessionManager.currentToken,
                            MakeTransferRequest(event.fromCard, event.toCard, event.amount.toDouble())
                        )
                    ) {
                        postEffect { TransfersContract.TransfersEffect.TransferSuccess }
                    }
                }
            }
        }
    }
}

class TransfersContract {
    sealed class TransfersEvent : UIEvent {
        data class OnCardSelect(val fromCard: Any?, val toCard: Any?, val amount: String) : TransfersEvent()
        data class StartTransfer(val fromCard: Any?, val toCard: Any?, val amount: String) : TransfersEvent()
    }

    data class TransfersState(
        val cardsList: List<Card>? = null,
    ) : UIState

    sealed class TransfersEffect : UIEffect {
        data class IsUiValid(val isValid: Boolean) : TransfersEffect()
        object TransferSuccess : TransfersEffect()
    }
}