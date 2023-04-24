package com.azercell.myazercell.domain.entity.remote.home

import com.azercell.myazercell.domain.entity.enum.CardCurrency
import com.azercell.myazercell.domain.entity.enum.CardNetwork

data class CardOrderRequest(
    val cardHolderName: String,
    val cardType: CardNetwork,
    val cardCurrency: CardCurrency,
)