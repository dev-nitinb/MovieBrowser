package com.test.moviebrowser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebrowser.R
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.databinding.ActivityMovieDetailBinding
import com.test.moviebrowser.ui.viewmodel.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: Movie
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        movie = (intent.getSerializableExtra("movie") as Movie?)!!

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setMovie(movie)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: AppCompatImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(imageView)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_image_not_found)
                    .placeholder(R.drawable.ic_image_not_found)
                    .into(imageView)
            }
        }
    }

}