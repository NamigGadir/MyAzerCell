package com.azercell.myazercell.domain.base

interface BaseUseCase<T, R> {

    suspend fun call(input: T): R

}