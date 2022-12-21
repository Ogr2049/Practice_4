package com.example.practice04.network.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: Long,
    @SerializedName("album_id")
    val albumId: Long,
    @SerializedName("owner_id")
    val ownerId: Long,
    val sizes: List<Size>,
    var photoUrl: String = "",
)
