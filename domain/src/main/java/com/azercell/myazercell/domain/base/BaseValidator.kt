package com.azercell.myazercell.domain.base

interface BaseValidator<T> {
    fun isValid(input: T): Boolean
}