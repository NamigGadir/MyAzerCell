package com.azercell.myazercell.core.base

interface SessionHandler {

    fun onRegister(authToken: String)

    fun onLogout() {

    }
}