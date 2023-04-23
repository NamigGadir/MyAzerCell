package com.azercell.myazercell.data.util

import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import java.util.*

object UserDataController {
    private var registeredUser: RegisterRequest? = null
    private var currentToken: String = ""
    private val userCards: List<Card> = listOf(
        Card(1, "1234567890123456", "13/12", CardNetwork.MC),
        Card(2, "3456789012345678", "22/12", CardNetwork.VISA),
        Card(3, "2345678901234567", "13/44", CardNetwork.VISA),
        Card(4, "0987654789876546", "33/12", CardNetwork.MC)
    )

    fun startRegistration(registerRequest: RegisterRequest): RegisterResponse {
        UUID.randomUUID().toString().also { token ->
            registeredUser = registerRequest
            currentToken = token
            return RegisterResponse(token)
        }
    }

    fun getUserInfo(authToken: String): RegisterRequest? {
        if (authToken == currentToken) {
            return registeredUser
        } else
            throw IllegalAccessException()
    }

    fun getUserCards(authToken: String): List<Card> {
        if (authToken == currentToken) {
            return userCards
        } else
            throw IllegalAccessException()
    }

}