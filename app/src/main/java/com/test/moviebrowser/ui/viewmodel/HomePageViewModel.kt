package com.test.moviebrowser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.repository.MovieRepository
import com.test.moviebrowser.ui.activity.HomePageActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: MutableStateFlow<List<Movie>> = _movieList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loading.value = true
                movieRepository.fetchMovie()
                    .collect { movies ->
                        _movieList.value = movies
                    }

            } catch (e: Exception) {
                Log.e(TAG, "Request failed: ${e.message}")
                _movieList.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

}