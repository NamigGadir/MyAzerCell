package com.azercell.myazercell.domain.repository

import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse

interface AuthRepository {

    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse

    suspend fun getCurrentUser(authToken: String): RegisterRequest?
}