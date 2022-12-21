package com.example.practice04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val progressBar = findViewById<ProgressBar>(R.id.main_progress)
        val searchEditText = findViewById<EditText>(R.id.main_search_edit_text)
        val searchButton = findViewById<Button>(R.id.main_search_button)

        mainViewModel.getUserOriginalIdResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.data != null) {
                    if (body.data.isNotEmpty()) {
                        val user = body.data[0]
                        if (!user.isClosed) {
                            val intent = Intent(this, AlbumsActivity::class.java)
                            intent.putExtra("user_id", user.id)
                            startActivity(intent)
                        } else {
                            searchEditText.error = getString(R.string.err_profile_closed)
                        }
                    } else {
                        searchEditText.error = getString(R.string.err_cant_find_user)
                    }
                } else {
                    searchEditText.error = getString(R.string.err_vk_api)
                }
            }
            progressBar.visibility = View.GONE
        }

        searchButton.setOnClickListener {
            if (searchEditText.text.isBlank()) {
                searchEditText.error = getString(R.string.err_field_cant_be_empty)
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            mainViewModel.getUserOriginalId(searchEditText.text.toString())
            searchEditText.text.clear()
        }
    }
}
