package com.example.practice04.network.model

import com.google.gson.annotations.SerializedName

data class MyResponseWithList<T>(
    @SerializedName("response")
    val data: List<T>? = null,
    @SerializedName("error")
    val error: MyError? = null,
)
