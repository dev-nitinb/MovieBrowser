package com.test.moviebrowser.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.test.moviebrowser.R
import com.test.moviebrowser.utils.ConnectionDetector
import com.test.moviebrowser.utils.ProjectUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var llSnackBar: LinearLayoutCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
        lifecycleScope.launch {
            delay(500)
            withContext(Dispatchers.Main){
                checkInternetConnectionAndProceed()
            }
        }
    }

    private fun bindView() {
        llSnackBar = findViewById(R.id.llSnackBar)
    }

    private fun checkInternetConnectionAndProceed() {
            if (ConnectionDetector.isConnected(this@MainActivity)) {
                startActivity(Intent(this@MainActivity, HomePageActivity::class.java))
                finish()
            } else {
                ProjectUtils.showSickbar(
                    this@MainActivity,
                    llSnackBar,
                    "Internet connection not found!"
                )
            }

    }
}