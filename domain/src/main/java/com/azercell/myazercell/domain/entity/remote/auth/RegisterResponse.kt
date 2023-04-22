package com.azercell.myazercell.domain.entity.remote.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
   @SerializedName("authToken")
    val authToken: String
)