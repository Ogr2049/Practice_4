package com.example.practice04

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.practice04.network.Repository
import com.example.practice04.network.model.*
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository()

    val getUserOriginalIdResponse: MutableLiveData<Response<MyResponseWithList<User>>> =
        MutableLiveData()
    val getUsersAlbumsListResponse: MutableLiveData<Response<MyResponse<Items<Album>>>> =
        MutableLiveData()
    val getAlbumPhotosListResponse: MutableLiveData<Response<MyResponse<Items<Photo>>>> =
        MutableLiveData()

    fun getUserOriginalId(userId: String) {
        viewModelScope.launch {
            getUserOriginalIdResponse.postValue(repository.getUserOriginalId(userId))
        }
    }

    fun getUsersAlbumsList(ownerId: Long) {
        viewModelScope.launch {
            getUsersAlbumsListResponse.postValue(repository.getUsersAlbumsList(ownerId))
        }
    }

    fun getAlbumPhotosList(ownerId: Long, albumId: String, offset: Int, count: Int) {
        viewModelScope.launch {
            getAlbumPhotosListResponse.postValue(
                repository.getAlbumPhotosList(
                    ownerId,
                    albumId,
                    offset,
                    count
                )
            )
        }
    }
}
