package com.azercell.myazercell.data.util

import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.home.CardOrderRequest
import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest
import java.text.SimpleDateFormat
import java.util.*

object UserDataController {
    private const val DATE_FORMAT: String = "mm/yy"
    private val cardNumberChars = "1234567890"
    private var registeredUser: RegisterRequest? = null
    private var currentToken: String? = ""
    private val userCards: ArrayList<Card> = arrayListOf()

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

    fun getCardById(authToken: String, cardId: Long): Card {
        if (authToken == currentToken) {
            return userCards.first { it.id == cardId }
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
                cardCurrency = cardOrderRequest.cardCurrency.name
            )
            userCards.add(card)
        } else
            throw IllegalAccessException()
    }

    fun makeTransfer(authToken: String, makeTransferRequest: MakeTransferRequest) {
        if (authToken == currentToken) {
            makeTransferRequest.apply {
                from.cardBalance -= amount
                to.cardBalance += amount
            }
        } else
            throw IllegalAccessException()
    }

    fun logout(authToken: String) {
        if (authToken == currentToken) {
            currentToken = null
            userCards.clear()
            registeredUser = null
        } else
            throw IllegalAccessException()
    }

    fun removeCard(authToken: String, cardId: Long) {
        if (authToken == currentToken) {
            val index = userCards.indexOfFirst { it.id == cardId }
            userCards.removeAt(index)
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