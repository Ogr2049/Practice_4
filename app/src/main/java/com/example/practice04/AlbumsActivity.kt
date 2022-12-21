package com.example.practice04

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumsActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val adapter = AlbumsListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.albums_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val noAlbumsText = findViewById<TextView>(R.id.albums_no_photos)
        mainViewModel.getUsersAlbumsListResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val albums = response.body()?.data?.items!!
                albums.forEach { album ->
                    val y = album.thumbSizes.find { it.type == "y" }
                    if (y != null) {
                        album.thumbUrl = y.url
                    } else {
                        val max = album.thumbSizes.maxWith { left, right ->
                            return@maxWith compareValues(
                                left.height * left.width,
                                right.height * right.width
                            )
                        }
                        album.thumbUrl = max.url
                    }
                }
                if (albums.isNotEmpty()) {
                    adapter.setData(albums)
                } else {
                    noAlbumsText.visibility = View.VISIBLE
                }
            }
        }

        val userId = intent.getLongExtra("user_id", -1)
        if (userId != -1L) {
            mainViewModel.getUsersAlbumsList(userId)
        } else {
            Toast.makeText(this, getString(R.string.err_some), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
