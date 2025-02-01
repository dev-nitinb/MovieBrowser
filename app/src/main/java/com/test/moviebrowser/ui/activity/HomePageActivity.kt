package com.test.moviebrowser.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.moviebrowser.R
import com.test.moviebrowser.ui.adapter.MovieAdapter
import com.test.moviebrowser.data.model.MovieResponse
import com.test.moviebrowser.data.model.Movie
import com.test.moviebrowser.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity() {

    lateinit var rvMovie: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var etSearch: AppCompatEditText
    lateinit var ivSearch: AppCompatImageView
    lateinit var layoutManager: GridLayoutManager

    private var alMovieResults = ArrayList<Movie>()
    private lateinit var movieAdapter: MovieAdapter

    var TAG = "HomePageActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        bindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun bindView() {
        rvMovie = findViewById(R.id.rvMovie)
        progressBar = findViewById(R.id.progressBar)
        etSearch = findViewById(R.id.etSearch)
        ivSearch = findViewById(R.id.ivSearch)

        layoutManager = GridLayoutManager(this, 2)
        rvMovie.layoutManager = layoutManager

        getMovies()

    }

    private fun getMovies() {
        showProgressBar()
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    val movieResponse: MovieResponse =
                        RetrofitClient.getApiService().getPopularMovie()
                    hideProgressBar()
                    movieResponse.movies.let { alMovieResults.addAll(it.toList()) }
                    movieAdapter = MovieAdapter(this@HomePageActivity, alMovieResults)
                    rvMovie.adapter = movieAdapter
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Request failed: ${e.message}")
                withContext(Dispatchers.Main) {
                    hideProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}