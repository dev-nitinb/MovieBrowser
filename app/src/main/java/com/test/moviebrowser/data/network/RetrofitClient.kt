package com.test.moviebrowser.data.network

import com.test.moviebrowser.utils.ProjectUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = ProjectUtils.BASE_URL

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService = retrofit.create(ApiService::class.java)
}
