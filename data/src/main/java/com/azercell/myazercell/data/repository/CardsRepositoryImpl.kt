package com.azercell.myazercell.data.repository

import com.azercell.myazercell.data.util.UserDataController
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.repository.CardsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor() : CardsRepository {

    override suspend fun getUserCards(token: String): List<Card> {
        return UserDataController.getUserCards(token)
    }

    override suspend fun getCardById(token: String, cardId: Long): Card {
        return UserDataController.getCardById(token, cardId)
    }

    override suspend fun orderCard(token: String, cardOrderRequest: CardOrderRequest) {
        return UserDataController.orderCard(token, cardOrderRequest)
    }

    override suspend fun removeCard(token: String, cardId: Long) {
        UserDataController.removeCard(token, cardId)
    }
}