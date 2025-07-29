package com.test.moviebrowser.data.repository

import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.network.RetrofitClient

object MovieRepository {

    suspend fun fetchMovie(): List<Movie>{
        val movieResponse = RetrofitClient.getApiService().getPopularMovie()
        return movieResponse.movies
    }
}