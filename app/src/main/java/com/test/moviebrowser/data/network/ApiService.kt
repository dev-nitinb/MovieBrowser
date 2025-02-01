package com.test.moviebrowser.data.network

import com.test.moviebrowser.data.model.MovieResponse
import com.test.moviebrowser.utils.ProjectUtils
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = ProjectUtils.API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ProjectUtils.LANGUAGE,
    ): MovieResponse

}