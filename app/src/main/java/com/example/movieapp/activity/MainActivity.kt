package com.example.movieapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.movieapp.R
import com.example.movieapp.utils.ConnectionDetector
import com.example.movieapp.utils.ProjectUtils
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var llSnackBar:LinearLayoutCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()

        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                checkInternetConnectionAndProceed()
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }


    private fun bindView(){
        llSnackBar=findViewById(R.id.llSnackBar)
    }

    private fun checkInternetConnectionAndProceed(){
        if (ConnectionDetector.isConnected(this@MainActivity)){
            startActivity(Intent(this@MainActivity, HomePageActivity::class.java))
            finish()
        }
        else{
            ProjectUtils.showSickbar(this@MainActivity,llSnackBar,"Internet connection not found!")
        }
    }
}