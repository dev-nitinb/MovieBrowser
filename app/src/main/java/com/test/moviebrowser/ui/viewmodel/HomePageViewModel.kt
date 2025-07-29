package com.test.moviebrowser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.repository.MovieRepository
import com.test.moviebrowser.ui.activity.HomePageActivity.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loading.value = true
                var newMovieList: List<Movie>
                withContext(Dispatchers.IO) {
                    newMovieList = MovieRepository.fetchMovie()
                }
                _movieList.value = newMovieList
            } catch (e: Exception) {
                Log.e(TAG, "Request failed: ${e.message}")
                _movieList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

}