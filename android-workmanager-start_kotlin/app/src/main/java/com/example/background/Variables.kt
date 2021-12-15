package com.example.background

import android.util.Log
import kotlin.properties.Delegates

object Variables {
    var isNetworkCOnnected: Boolean by Delegates.observable(false) {
        property, oldValue, newValue ->
        Log.i("checkNetwork", "$newValue")
    }
}