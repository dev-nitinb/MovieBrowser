package com.test.moviebrowser.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.test.moviebrowser.R
import com.test.moviebrowser.utils.ConnectionDetector
import com.test.moviebrowser.utils.ProjectUtils

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