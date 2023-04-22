package com.azercell.myazercell.domain.entity.remote.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)