package com.example.practice04.network.model

import com.google.gson.annotations.SerializedName

data class MyError(
    @SerializedName("error_code")
    val errorCode: Long,
    @SerializedName("error_msg")
    val errorMsg: String
)
