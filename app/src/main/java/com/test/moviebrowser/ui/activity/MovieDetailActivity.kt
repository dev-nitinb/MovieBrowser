package com.test.moviebrowser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.moviebrowser.R
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.databinding.ActivityMovieDetailBinding
import com.test.moviebrowser.utils.ProjectUtils

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: Movie

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = (intent.getSerializableExtra("movie") as Movie?)!!

        Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .applyDefaultRequestOptions(RequestOptions.skipMemoryCacheOf(true))
            .load("${ProjectUtils.IMAGE_URL}${movie.poster_path}")
            .error(R.drawable.ic_image_not_found)
            .placeholder(R.drawable.ic_image_not_found)
            .into(binding.ivPoster)

        binding.movie = movie
    }
}