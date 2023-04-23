package com.azercell.myazercell.domain.repository

import com.azercell.myazercell.domain.entity.remote.home.Card

interface CardsRepository {

    suspend fun getUserCards(token: String): List<Card>

}