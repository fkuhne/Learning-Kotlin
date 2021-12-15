package com.chepsi.callbackdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        if (Variables.isNetworkConnected){
            //Do something when network is connected
            binding.textView.text = "CONNECTED!"
        }
        else {
            //Do something else when network is not connected
            binding.textView.text = "not connected"
        }
    }
}