package com.azercell.myazercell.domain.usecase.transfers

import com.azercell.myazercell.domain.base.BaseUseCase
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.entity.remote.auth.RegisterResponse
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest
import com.azercell.myazercell.domain.repository.AuthRepository
import com.azercell.myazercell.domain.repository.CardsRepository
import com.azercell.myazercell.domain.repository.TransfersRepository
import javax.inject.Inject

class MakeTransfersUseCase @Inject constructor(
    private val transfersRepository: TransfersRepository
) : BaseUseCase<MakeTransfersUseCase.Params, Unit> {

    override suspend fun call(input: Params) {
        return transfersRepository.makeTransfer(input.authToken, input.makeTransferRequest)
    }

    data class Params(
        val authToken: String,
        val makeTransferRequest: MakeTransferRequest
    )
}