package com.test.moviebrowser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.test.moviebrowser.databinding.ActivityHomePageBinding
import com.test.moviebrowser.ui.adapter.MovieAdapter
import com.test.moviebrowser.ui.viewmodel.HomePageViewModel
import com.test.moviebrowser.utils.ConnectionDetector
import com.test.moviebrowser.utils.ProjectUtils

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (ConnectionDetector.isConnected(this)) {
            viewModel.fetchMovies()
        } else {
            ProjectUtils.showSnackBar(
                this,
                binding.root,
                "Internet connection not found!"
            )
        }
        observeData()
    }

    private fun observeData() {
        viewModel.movieList.observe(this) { list ->
            binding.rvMovie.layoutManager = GridLayoutManager(applicationContext, 2)
            val movieAdapter = MovieAdapter(list)
            binding.rvMovie.adapter = movieAdapter
        }
    }
    companion object {
        const val TAG = "HomePageActivity"
    }
}
