package com.azercell.myazercell.data.util

import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import java.text.SimpleDateFormat
import java.util.*

object UserDataController {
    private const val DATE_FORMAT: String = "mm/yy"
    private val cardNumberChars = "1234567890"
    private var registeredUser: RegisterRequest? = RegisterRequest(
        "Namig", "Gadirov", "33/333/333", "994504592346"
    )
    private var currentToken: String = ""
    private val userCards: ArrayList<Card> = arrayListOf(
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

    fun orderCard(authToken: String, cardOrderRequest: CardOrderRequest) {
        if (authToken == currentToken) {
            val card = Card(
                id = Random().nextLong(),
                cardNumber = cardNumberChars.getRandomString(16),
                expireDate = calculateExpireDate(),
                cardNetwork = cardOrderRequest.cardType,
                cardBalance = 10.0,
                cardHolderName = cardOrderRequest.cardHolderName,
                cardCurrency = cardOrderRequest.cardCurrency
            )
            userCards.add(card)
        } else
            throw IllegalAccessException()
    }

    private fun String.getRandomString(size: Int): String {
        val random = Random()
        val sb = StringBuilder(size)
        for (i in 0 until size)
            sb.append(this[random.nextInt(length)])
        return sb.toString()
    }

    fun calculateExpireDate(): String {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.YEAR, 5)
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return sdf.format(c.time)
    }
}