package com.test.moviebrowser.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.databinding.ActivityMovieDetailBinding
import com.test.moviebrowser.ui.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: Movie
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movie = (intent.getSerializableExtra("movie") as Movie?)!!

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setMovie(movie)
    }

}