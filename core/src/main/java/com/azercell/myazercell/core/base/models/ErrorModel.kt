package com.azercell.myazercell.core.base.models

data class ErrorModel(
    val errorCode: Int,
    val error: Throwable
)