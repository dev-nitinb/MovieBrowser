package com.test.moviebrowser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.model.MovieResponse
import com.test.moviebrowser.data.network.RetrofitClient
import com.test.moviebrowser.ui.activity.HomePageActivity.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieResponse: MovieResponse = RetrofitClient.getApiService().getPopularMovie()
                withContext(Dispatchers.Main){
                    _movieList.value = movieResponse.movies
                }

            } catch (e: Exception) {
                Log.e(TAG, "Request failed: ${e.message}")
                withContext(Dispatchers.Main) {

                }
            }
        }
    }

}