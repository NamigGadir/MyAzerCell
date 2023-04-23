package com.azercell.myazercell.domain.entity.remote.transfer

import com.azercell.myazercell.domain.entity.remote.home.Card

data class MakeTransferRequest(
    val from: Card,
    val to: Card,
    val amount: Double
)