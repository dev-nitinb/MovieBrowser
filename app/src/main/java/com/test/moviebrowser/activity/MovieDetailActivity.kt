package com.test.moviebrowser.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.moviebrowser.R
import com.test.moviebrowser.model.Results
import com.test.moviebrowser.utils.ProjectUtils

class MovieDetailActivity : AppCompatActivity() {

    lateinit var ivPoster: AppCompatImageView
    lateinit var tvMovieName: AppCompatTextView
    lateinit var tvOverview: AppCompatTextView
    lateinit var tvRating: AppCompatTextView
    lateinit var tvReleaseDate: AppCompatTextView

    lateinit var movie: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        bindView()

        movie = (intent.getSerializableExtra("movie") as Results?)!!

        Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .applyDefaultRequestOptions(RequestOptions.skipMemoryCacheOf(true))
            .load("${ProjectUtils.IMAGE_URL}${movie.poster_path}")
            .error(R.drawable.ic_image_not_found)
            .placeholder(R.drawable.ic_image_not_found)
            .into(ivPoster)

        tvMovieName.text = movie.original_title
        tvOverview.text = movie.overview
        tvRating.text = movie.vote_average.toString()
        tvReleaseDate.text = movie.release_date

    }

    fun bindView() {
        ivPoster = findViewById(R.id.ivPoster)
        tvMovieName = findViewById(R.id.tvMovieName)
        tvOverview = findViewById(R.id.tvOverview)
        tvRating = findViewById(R.id.tvRating)
        tvReleaseDate = findViewById(R.id.tvReleaseDate)
    }
}