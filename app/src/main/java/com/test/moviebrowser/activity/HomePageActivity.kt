package com.test.moviebrowser.activity

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
import com.test.moviebrowser.adapter.MovieAdapter
import com.test.moviebrowser.model.MovieResponse
import com.test.moviebrowser.model.Results
import com.test.moviebrowser.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity() {

    lateinit var rvMovie: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var etSearch: AppCompatEditText
    lateinit var ivSearch: AppCompatImageView
    lateinit var layoutManager: GridLayoutManager

    private var alMovieResults = ArrayList<Results>()
    private lateinit var movieAdapter: MovieAdapter
    var pageNumber = 1

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

        getMovies(pageNumber)

    }


    private fun getMovies(pageNumber: Int) {
        showProgressBar()
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.Main){
                    val movieResponse: MovieResponse = RetrofitClient.apiService.getPopularMovie()
                    hideProgressBar()
                    movieResponse.results.let { alMovieResults.addAll(it.toList()) }
                    movieAdapter = MovieAdapter(this@HomePageActivity, alMovieResults)
                    rvMovie.adapter = movieAdapter
                }
            }
            catch (e: Exception){
                Log.e("Retrofit", "Request failed: ${e.message}")
                withContext(Dispatchers.Main){
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