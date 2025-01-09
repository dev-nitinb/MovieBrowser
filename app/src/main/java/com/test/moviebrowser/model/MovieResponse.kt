package com.test.moviebrowser.model

data class MovieResponse(
    val page: Int,
    val results: List<Results>,
    val total_results: Int,
    val total_pages: Int
)
