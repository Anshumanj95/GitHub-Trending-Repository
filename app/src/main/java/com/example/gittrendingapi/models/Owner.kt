package com.example.gittrendingapi.models

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    val avatar_url: String,
)