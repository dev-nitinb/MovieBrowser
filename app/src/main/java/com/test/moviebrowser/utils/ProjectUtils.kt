package com.test.moviebrowser.utils

import android.content.Context
import android.view.View
import com.test.moviebrowser.R
import com.google.android.material.snackbar.Snackbar

object ProjectUtils {

    //base url
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/"

    //post url
    const val MOST_POPULAR_POST_URL = "/movie/popular"
    const val HIGHEST_RATED_POST_URL = "/movie/top_rated"
    const val SEARCH_MOVIE_POST_URL = "/search/movie"

    const val LANGUAGE = "en-US"
    const val API_KEY = "72adaea1f2d46949d26d74b0d53a0cde"

    fun showSnackBar(context: Context, view: View, msg: String?) {
        val snackBar = Snackbar.make(view, msg!!, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
        snackBar.show()
    }
}