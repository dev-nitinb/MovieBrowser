package com.test.moviebrowser.data.repository

import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.network.ApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService){

    suspend fun fetchMovie(): List<Movie>{
        val movieResponse = apiService.getPopularMovie()
        return movieResponse.movies
    }
}