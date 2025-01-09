package com.test.moviebrowser.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.moviebrowser.R
import com.test.moviebrowser.adapter.MovieAdapter
import com.test.moviebrowser.model.MovieResponse
import com.test.moviebrowser.model.Results
import com.test.moviebrowser.utils.ProjectUtils
import com.test.moviebrowser.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageActivity : AppCompatActivity() {

    lateinit var rvMovie:RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var etSearch: AppCompatEditText
    lateinit var ivSearch: AppCompatImageView
    lateinit var layoutManager: GridLayoutManager

    var alMovieResults= ArrayList<Results>()
    lateinit var movieAdapter: MovieAdapter
    var isLoading=true
    var isSearching=false
    var pageNumber=1
    var pastVisibleItems=0
    var visibleItemCount=0
    var totalItemCount=0
    var previousTotal=0
    var viewThresold=10
    var searchString=""

    var postUrl=ProjectUtils.MOST_POPULAR_POST_URL
    var TAG="HomePageActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        bindView()
        initScrollListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.setting_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun bindView(){
        rvMovie=findViewById(R.id.rvMovie)
        progressBar=findViewById(R.id.progressBar)
        etSearch=findViewById(R.id.etSearch)
        ivSearch=findViewById(R.id.ivSearch)

        layoutManager=GridLayoutManager(this,2)
        rvMovie.layoutManager=layoutManager

        postUrl=ProjectUtils.MOST_POPULAR_POST_URL
        getMovies(pageNumber)

        ivSearch.setOnClickListener {
            searchString=etSearch.text.toString()
            if(searchString.isEmpty()){
                etSearch.error="Movie name empty"
            }
            else{
                resetData()
            }
        }
    }

    private fun initScrollListener() {
        rvMovie.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx:Int, dy:Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount=layoutManager.childCount
                totalItemCount=layoutManager.itemCount
                pastVisibleItems=layoutManager.findFirstVisibleItemPosition()

                if(dy>0){
                    if(isLoading){
                        if(totalItemCount>previousTotal){
                            isLoading=false
                            previousTotal=totalItemCount
                        }
                    }
                    if(!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+viewThresold)){
                        pageNumber++
                        isLoading=true
                    }
                }
            }
        })
    }

    private fun resetData(){
        alMovieResults.clear()
        isLoading=true
        pageNumber=1
        pastVisibleItems=0
        visibleItemCount=0
        totalItemCount=0
        previousTotal=0
        viewThresold=20
        isSearching=false
    }

    private fun getMovies(pageNumber:Int) {
        showProgressBar()
        val call: Call<MovieResponse> = RetrofitClient.apiService.getPopularMovie()

        // Asynchronous request
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    hideProgressBar()
                    response.body()?.results?.let { alMovieResults.addAll(it.toList()) }
                    movieAdapter= MovieAdapter(this@HomePageActivity,alMovieResults)
                    movieAdapter.notifyDataSetChanged()
                    rvMovie.adapter=movieAdapter
                } else {
                    // Handle error response
                    Log.e("Retrofit", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle failure
                Log.e("Retrofit", "Request failed: ${t.message}")
                hideProgressBar()
            }
        })
    }

    fun showProgressBar(){
        progressBar.visibility= View.VISIBLE
    }

    fun hideProgressBar(){
        progressBar.visibility= View.GONE
    }
}