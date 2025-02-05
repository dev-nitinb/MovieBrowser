package com.test.moviebrowser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.moviebrowser.R
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.databinding.ItemMovieBinding
import com.test.moviebrowser.ui.activity.MovieDetailActivity

class MovieAdapter(private val alMovieResults: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun getItemCount(): Int {
        return alMovieResults.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(alMovieResults[position])
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bindData(movieResult: Movie ) {
            binding.movie = movieResult
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2/${movieResult.poster_path}")
                .placeholder(R.drawable.ic_image_not_found)
                .into(binding.ivPoster)
            binding.tvMovieName.text = movieResult.original_title
            binding.cardView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movie", movieResult)
                context.startActivity(intent)
            }
        }
    }

}