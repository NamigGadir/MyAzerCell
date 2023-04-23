package com.azercell.myazercell.data.repository

import com.azercell.myazercell.data.util.UserDataController
import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest
import com.azercell.myazercell.domain.repository.TransfersRepository
import javax.inject.Inject

class TransfersRepositoryImpl @Inject constructor() : TransfersRepository {

    override fun makeTransfer(authToken: String, makeTransferRequest: MakeTransferRequest) {
        return UserDataController.makeTransfer(authToken, makeTransferRequest)
    }

}