package com.azercell.myazercell.data.repository

import com.azercell.myazercell.data.util.SessionController
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return SessionController.startRegistration(registerRequest)
    }

    override suspend fun getCurrentUser(authToken: String): RegisterRequest? {
        return SessionController.getUserInfo(authToken)
    }
}