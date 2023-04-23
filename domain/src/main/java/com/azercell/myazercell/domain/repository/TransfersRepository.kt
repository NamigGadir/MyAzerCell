package com.azercell.myazercell.domain.repository

import com.azercell.myazercell.domain.entity.remote.transfer.MakeTransferRequest

interface TransfersRepository {

    fun makeTransfer(authToken: String, makeTransferRequest: MakeTransferRequest)
}