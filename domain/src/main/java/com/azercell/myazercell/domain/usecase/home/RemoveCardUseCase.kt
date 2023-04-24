package com.azercell.myazercell.domain.usecase.home

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.repository.AuthRepository
import com.azercell.myazercell.domain.repository.CardsRepository
import javax.inject.Inject

class RemoveCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) : BaseUseCase<RemoveCardUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return cardsRepository.removeCard(input.token, input.cardId)
    }

    data class Params(
        val token: String,
        val cardId: Long
    )
}