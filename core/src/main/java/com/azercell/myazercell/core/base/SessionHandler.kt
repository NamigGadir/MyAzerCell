package com.azercell.myazercell.core.base

import androidx.appcompat.widget.Toolbar

interface SessionHandler {

    fun onRegister(authToken: String)

    fun onLogout() {

    }

    fun onToolbarChange(toolbar: Toolbar)
}