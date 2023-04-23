package com.azercell.myazercell.domain.usecase.home

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<String, RegisterRequest?> {

    override suspend fun call(input: String): RegisterRequest? {
        return authRepository.getCurrentUser(input)
    }
}