package com.azercell.myazercell.domain.entity.remote.home

import com.azercell.myazercell.domain.entity.enum.CardNetwork

data class Card(
    val id: Long,
    val cardNumber: String,
    val expireDate: String,
    val cardNetwork: CardNetwork,
    val cardBalance: Double = 10.0,
    val cardHolderName: String? = "Namig",
    val cardCurrency: String = "AZN",
)