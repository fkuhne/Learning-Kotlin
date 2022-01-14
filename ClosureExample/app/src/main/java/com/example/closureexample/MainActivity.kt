package com.example.closureexample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.closureexample.databinding.ActivityMainBinding

/**
 * Callbacks and closures are oftentimes used for similar purposes. The main one is asynchronous
 *   programming or event listening.
 *
 * A callback is any executable code that can be passed as an argument to other code.
 *   The other code is expected to call back or execute the argument at some given time.
 */

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Let's first explore an example without the use of callbacks or closures. This will
         *   create a blocking, synchronous execution, one call after another
         */
        val fred = Barista("Fred")
        val sam = Barista("Sam")

        fred.acceptOrderBlocking(CoffeeType.LATTE)
        sam.acceptOrderBlocking(CoffeeType.AMERICANO)


        val unblockingFred = BaristaWithClosure("Unblocking Fred")
        val unblockingSam = BaristaWithCallback("Unblocking Sam")

        unblockingFred.acceptOrder(CoffeeType.LATTE)
        unblockingSam.acceptOrder(CoffeeType.AMERICANO)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.text = "Goodbye Butterfly! ;)"
        setContentView(binding.root)
    }
}