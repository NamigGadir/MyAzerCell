package com.azercell.myazercell.domain.entity

import java.lang.Exception

sealed class RemoteResponse<out T> {
    data class Success<out T>(val result: T) : RemoteResponse<T>()
    data class NetworkError(val errorCode: Int, val exception: Exception) : RemoteResponse<Nothing>()
}
