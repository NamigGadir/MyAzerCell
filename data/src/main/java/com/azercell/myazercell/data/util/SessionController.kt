package com.azercell.myazercell.data.util

import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import java.util.UUID

object SessionController {
    private var registeredUser: RegisterRequest? = null
    private var currentToken: String? = null

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
}