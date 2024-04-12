package com.example.Adv160421097UTS.model

import com.google.gson.annotations.SerializedName
data class Hobby(
    val id: String,
    val title: String,
    val image: String,
    val user: String,
    val shortDescription: String,
    val mainDescription: List<String>
)




