package com.example.movieapp.model

data class Movie(
    val page: Int,
    val results:ArrayList <Results>,
    val total_pages: Int,
    val total_results: Int
)