package com.example.movieapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.model.Results
import com.example.movieapp.utils.ProjectUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

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

    var postUrl=""
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        postUrl = if(item.itemId==R.id.mostPopular) {
            ProjectUtils.MOST_POPULAR_POST_URL
        } else{
            ProjectUtils.HIGHEST_RATED_POST_URL
        }
        resetData()
        getMovies(pageNumber)

        return super.onOptionsItemSelected(item)
    }


    fun bindView(){
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
                searchMovie(searchString,pageNumber)
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

                        if(isSearching)
                            searchMovie(searchString,pageNumber)
                        else
                            getMovies(pageNumber)
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

    fun getMovies(pageNumber:Int) {
        showProgressBar()
        var url="${ProjectUtils.BASE_URL}$postUrl"
        AndroidNetworking
            .get(url)
            .addQueryParameter("api_key", ProjectUtils.API_KEY)
            .addQueryParameter("language", ProjectUtils.LANGUAGE)
            .addQueryParameter("page", pageNumber.toString())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        hideProgressBar()
                        var resultsJson=response!!.getJSONArray("results")
                        var resultType=object: TypeToken<ArrayList<Results>>(){}.type
                        var alMovieNewResults = Gson().fromJson(resultsJson.toString(), resultType) as ArrayList<Results>
                        for(i in 0 until alMovieNewResults.size){
                            alMovieResults.add(alMovieNewResults[i])
                        }
                        movieAdapter= MovieAdapter(this@HomePageActivity,alMovieResults)
                        movieAdapter.notifyDataSetChanged()
                        rvMovie.adapter=movieAdapter
                    } catch (e: Exception) {
                        Log.e(TAG,"Error: ${e.message}")
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.e(TAG,"Error: ${anError.toString()}")
                    hideProgressBar()
                }
            })
    }

    fun searchMovie(searchString:String,pageNumber:Int) {
        isSearching=true
        showProgressBar()
        var url="${ProjectUtils.BASE_URL}${ProjectUtils.SEARCH_MOVIE_POST_URL}"
        AndroidNetworking
            .get(url)
            .addQueryParameter("api_key", ProjectUtils.API_KEY)
            .addQueryParameter("query", searchString)
            .addQueryParameter("language", ProjectUtils.LANGUAGE)
            .addQueryParameter("page", pageNumber.toString())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        hideProgressBar()
                        var resultsJson=response!!.getJSONArray("results")
                        var resultType=object: TypeToken<ArrayList<Results>>(){}.type
                        var alMovieNewResults = Gson().fromJson(resultsJson.toString(), resultType) as ArrayList<Results>
                        for(i in 0 until alMovieNewResults.size){
                            alMovieResults.add(alMovieNewResults[i])
                        }
                        movieAdapter= MovieAdapter(this@HomePageActivity,alMovieResults)
                        movieAdapter.notifyDataSetChanged()
                        rvMovie.adapter=movieAdapter
                    } catch (e: Exception) {
                        Log.e(TAG,"Error: ${e.message}")
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.e(TAG,"Error: ${anError.toString()}")
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