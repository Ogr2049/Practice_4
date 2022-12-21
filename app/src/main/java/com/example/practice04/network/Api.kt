package com.example.practice04.network

import com.example.practice04.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("users.get")
    suspend fun getUserOriginalId(
        @Query("user_id") userId: String,
    ): Response<MyResponseWithList<User>>

    @GET("photos.getAlbums")
    suspend fun getUsersAlbumsList(
        @Query("owner_id") ownerId: Long,
        @Query("need_system") needSystem: Int = 1,
        @Query("need_covers") needCovers: Int = 1,
        @Query("photo_sizes") photoSizes: Int = 1,
    ): Response<MyResponse<Items<Album>>>

    @GET("photos.get")
    suspend fun getAlbumPhotosList(
        @Query("owner_id") ownerId: Long,
        @Query("album_id") albumId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("rev") rev: Int = 1,
    ): Response<MyResponse<Items<Photo>>>
}
