package com.example.Adv160421097UTS.model

import com.google.gson.annotations.SerializedName

data class Users(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
)
