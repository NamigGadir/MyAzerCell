package com.azercell.myazercell.domain.usecase.home

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.repository.AuthRepository
import com.azercell.myazercell.domain.repository.CardsRepository
import javax.inject.Inject

class OrderCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) : BaseUseCase<OrderCardUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return cardsRepository.orderCard(input.token, input.cardOrderRequest)
    }

    data class Params(
        val token: String,
        val cardOrderRequest: CardOrderRequest
    )
}