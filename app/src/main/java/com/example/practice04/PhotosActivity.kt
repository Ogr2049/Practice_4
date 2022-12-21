package com.example.practice04

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotosActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val adapter = PhotosListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.photos_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        var flag = false
        var offset = 0
        val step = 50
        var number: Long = -1
        val userId = intent?.getLongExtra("user_id", -1)
        val albumId = intent?.getStringExtra("album_id")

        if (userId == -1L || albumId.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.err_some), Toast.LENGTH_SHORT).show()
            finish()
        }

        mainViewModel.getAlbumPhotosListResponse.observe(this) { response ->
            if (response.isSuccessful) {
                number = (response.body()?.data?.count) ?: -1
                val photos = response.body()?.data?.items
                if (number != -1L && photos != null) {
                    photos.forEach { photo ->
                        val y = photo.sizes.find { it.type == "y" }
                        if (y != null) {
                            photo.photoUrl = y.url
                        } else {
                            val max = photo.sizes.maxWith { left, right ->
                                return@maxWith compareValues(
                                    left.height * left.width,
                                    right.height * right.width
                                )
                            }
                            photo.photoUrl = max.url
                        }
                    }
                    if (adapter.itemCount == 0) {
                        adapter.setData(photos)
                    } else {
                        adapter.plusData(photos)
                        flag = false
                    }
                } else {
                    Toast.makeText(this, getString(R.string.err_some), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        mainViewModel.getAlbumPhotosList(userId!!, albumId!!, offset, 50)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!flag && offset < number && !recyclerView.canScrollVertically(1)) {
                    flag = true

                    if (offset + step < number) {
                        offset += step
                    } else {
                        offset = number.toInt()
                    }

                    mainViewModel.getAlbumPhotosList(userId, albumId, offset, 50)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        Glide.get(this).clearMemory()
    }
}
