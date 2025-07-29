package com.test.moviebrowser.ui.activity

import android.os.Bundle
import android.view.View
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
        if (ConnectionDetector.isConnected(this)) {
            showProgressBar()
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

    private fun observeData(){
        viewModel.movieList.observe(this){ list->
            hideProgressBar()
            binding.rvMovie.layoutManager = GridLayoutManager(applicationContext, 2)
            val movieAdapter = MovieAdapter(list)
            binding.rvMovie.adapter = movieAdapter
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
