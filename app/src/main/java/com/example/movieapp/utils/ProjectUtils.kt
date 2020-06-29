package com.example.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.example.movieapp.R
import com.google.android.material.snackbar.Snackbar

object ProjectUtils {

    //base url
    val BASE_URL="https://api.themoviedb.org/3"
    val IMAGE_URL="https://image.tmdb.org/t/p/w185_and_h278_bestv2/"

    //post url
    val MOST_POPULAR_POST_URL="/movie/popular"
    val HIGHEST_RATED_POST_URL="/movie/top_rated"
    val SEARCH_MOVIE_POST_URL="/search/movie"

    val LANGUAGE="en-US"
    val API_KEY="72adaea1f2d46949d26d74b0d53a0cde"

    fun showSickbar(context: Context,view:View,msg: String?) {
        val snackbar = Snackbar.make(view, msg!!, Snackbar.LENGTH_LONG)
        val snackbarView: View = snackbar.view
        snackbarView.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
        snackbar.show()
    }
}