package com.azercell.myazercell.domain.usecase.home

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.repository.AuthRepository
import com.azercell.myazercell.domain.repository.CardsRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) : BaseUseCase<String, List<Card>> {

    override suspend fun call(input: String): List<Card> {
        return cardsRepository.getUserCards(input)
    }
}