package com.test.moviebrowser.data.repository

import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService){
    fun fetchMovie(): Flow<List<Movie>> = flow{
        val movieResponse = apiService.getPopularMovie()
        emit(movieResponse.movies)
    }
}