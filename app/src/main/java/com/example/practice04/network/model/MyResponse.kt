package com.example.practice04.network.model

import com.google.gson.annotations.SerializedName

data class MyResponse<T>(
    @SerializedName("response")
    val data: T? = null,
    @SerializedName("error")
    val error: MyError? = null
)
