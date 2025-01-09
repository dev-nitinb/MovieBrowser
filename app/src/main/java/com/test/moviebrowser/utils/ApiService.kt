package com.test.moviebrowser.utils

import com.test.moviebrowser.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String = ProjectUtils.API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ProjectUtils.LANGUAGE,
    ): Call<MovieResponse>

}