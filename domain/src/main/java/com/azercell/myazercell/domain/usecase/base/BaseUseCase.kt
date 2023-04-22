package com.azercell.myazercell.domain.usecase.base

interface BaseUseCase<T, R> {

    suspend fun call(input: T): R

}