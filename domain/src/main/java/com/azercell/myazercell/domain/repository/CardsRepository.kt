package com.azercell.myazercell.domain.repository

import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest

interface CardsRepository {

    suspend fun getUserCards(token: String): List<Card>

    suspend fun orderCard(token: String, cardOrderRequest: CardOrderRequest)
}