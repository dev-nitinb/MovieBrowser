package com.example.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.activity.HomePageActivity
import com.example.movieapp.activity.MovieDetailActivity
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Results

class MovieAdapter(val mContext: Context, val alMovieResults:ArrayList<Results>):
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    var view=LayoutInflater.from(mContext).inflate(R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alMovieResults.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(alMovieResults[position])
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivPoster=itemView.findViewById<AppCompatImageView>(R.id.ivPoster)
        var tvMovieName=itemView.findViewById<AppCompatTextView>(R.id.tvMovieName)
        var cardView=itemView.findViewById<CardView>(R.id.cardView)
        fun bindData(movieResult: Results){
            Glide
                .with(mContext)
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2/${movieResult.poster_path}")
                .placeholder(R.drawable.ic_image_not_found)
                .into(ivPoster)

            tvMovieName.text=movieResult.original_title


            cardView.setOnClickListener {
                var intent=Intent(mContext, MovieDetailActivity::class.java)
                intent.putExtra("movie", movieResult)
                mContext.startActivity(intent)

            }
        }
    }

}