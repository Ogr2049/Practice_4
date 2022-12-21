package com.example.practice04.network

import com.example.practice04.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttp = originalRequest.url

            val url = originalHttp.newBuilder()
                .addQueryParameter("access_token", BuildConfig.API_KEY)
                .addQueryParameter("v", BuildConfig.API_VERSION)
                .build()

            val requestBuilder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        })
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}
