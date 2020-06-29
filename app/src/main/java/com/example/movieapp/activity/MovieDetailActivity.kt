package com.example.movieapp.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.model.Results
import com.example.movieapp.utils.ProjectUtils
import com.google.android.material.appbar.CollapsingToolbarLayout

class MovieDetailActivity : AppCompatActivity() {

    lateinit var ivPoster: AppCompatImageView
    lateinit var tvMovieName: AppCompatTextView
    lateinit var tvOverview: AppCompatTextView
    lateinit var tvRating: AppCompatTextView
    lateinit var tvReleaseDate: AppCompatTextView

    lateinit var movie:Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        bindView()

        movie=intent.getParcelableExtra("movie")

        Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .applyDefaultRequestOptions(RequestOptions.skipMemoryCacheOf(true))
            .load("${ProjectUtils.IMAGE_URL}${movie.poster_path}")
            .error(R.drawable.ic_image_not_found)
            .placeholder(R.drawable.ic_image_not_found)
            .into(ivPoster)

        tvMovieName.text=movie.original_title
        tvOverview.text=movie.overview
        tvRating.text=movie.vote_average.toString()
        tvReleaseDate.text=movie.release_date

    }

    fun bindView(){
        ivPoster=findViewById(R.id.ivPoster)
        tvMovieName=findViewById(R.id.tvMovieName)
        tvOverview=findViewById(R.id.tvOverview)
        tvRating=findViewById(R.id.tvRating)
        tvReleaseDate=findViewById(R.id.tvReleaseDate)
    }
}