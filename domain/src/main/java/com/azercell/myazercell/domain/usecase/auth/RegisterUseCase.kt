package com.azercell.myazercell.domain.usecase.auth

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<RegisterRequest, RegisterResponse> {

    override suspend fun call(input: RegisterRequest): RegisterResponse {
        return authRepository.registerUser(input)
    }
}