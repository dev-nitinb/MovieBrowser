package com.test.moviebrowser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.utils.ProjectUtils
import androidx.lifecycle.map

class MovieDetailViewModel : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie get() = _movie

    val posterUrl: LiveData<String> = _movie.map{
        Log.d(TAG, "Mapping poster URL: ${it.poster_path}")
        "${ProjectUtils.IMAGE_URL}${it.poster_path}"
    }

    fun setMovie(receivedMovie: Movie) {
        _movie.value = receivedMovie
    }

    companion object{
        val TAG: String = Companion::class.java.name
    }
}