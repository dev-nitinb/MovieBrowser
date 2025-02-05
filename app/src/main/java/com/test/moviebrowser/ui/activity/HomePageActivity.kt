package com.test.moviebrowser.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.model.MovieResponse
import com.test.moviebrowser.data.network.RetrofitClient
import com.test.moviebrowser.databinding.ActivityHomePageBinding
import com.test.moviebrowser.ui.adapter.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity() {

    private var alMovieResults = ArrayList<Movie>()
    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMovies()
    }

    private fun getMovies() {
        showProgressBar()
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val movieResponse: MovieResponse = RetrofitClient.getApiService().getPopularMovie()
                withContext(Dispatchers.Main) {
                    hideProgressBar()
                    movieResponse.movies.let { alMovieResults.addAll(it.toList()) }
                    binding.rvMovie.layoutManager = GridLayoutManager(applicationContext, 2)
                    val movieAdapter = MovieAdapter(alMovieResults)
                    binding.rvMovie.adapter = movieAdapter
                }
            } catch (e: Exception) {
                Log.e(TAG, "Request failed: ${e.message}")
                withContext(Dispatchers.Main) {
                    hideProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    companion object {
        const val TAG = "HomePageActivity"
    }
}