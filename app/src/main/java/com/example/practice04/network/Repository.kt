package com.example.practice04.network

import com.example.practice04.network.model.*
import retrofit2.Response

class Repository {

    suspend fun getUserOriginalId(userId: String): Response<MyResponseWithList<User>> {
        return RetrofitInstance.api.getUserOriginalId(userId)
    }

    suspend fun getUsersAlbumsList(ownerId: Long): Response<MyResponse<Items<Album>>> {
        return RetrofitInstance.api.getUsersAlbumsList(ownerId)
    }

    suspend fun getAlbumPhotosList(
        ownerId: Long,
        albumId: String,
        offset: Int,
        count: Int,
    ): Response<MyResponse<Items<Photo>>> {
        return RetrofitInstance.api.getAlbumPhotosList(ownerId, albumId, offset, count)
    }
}
