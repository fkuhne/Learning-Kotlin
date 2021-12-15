/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

        // To manage all our coroutines, we need a 'Job'
        private var viewModelJob = Job()

        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        private var tonight = MutableLiveData<SleepNight?>()

        // Get all nights already in database when the view model is created
        private val nights = database.getAllNights()

        val nightsString = Transformations.map(nights) {
                formatNights(it, application.resources)
        }

        val startButtonVisible = Transformations.map(tonight) {
                null == it
        }

        val stopButtonVisible = Transformations.map(tonight) {
                null != it
        }

        val clearButtonVisible = Transformations.map(nights) { nights ->
                nights?.isNotEmpty()
        }

        private var _showSnackBarEvent = MutableLiveData<Boolean>()
        val showSnackBarEvent: LiveData<Boolean>
                get() = _showSnackBarEvent

        fun doneShowingSnackBar() {
                _showSnackBarEvent.value = false
        }

        private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
        val navigateToSleepQuality: LiveData<SleepNight>
                get() = _navigateToSleepQuality

        fun doneNavigating() {
                _navigateToSleepQuality.value = null
        }

        init {
                initializeTonight()
        }

        private fun initializeTonight() {
                uiScope.launch {
                        tonight.value = getTonightFromDatabase()
                }
        }

        fun onStartTracking() {
                uiScope.launch {
                        val newNight = SleepNight()
                        insert(newNight)
                        tonight.value = getTonightFromDatabase()
                }
        }

        fun onStopTracking() {
                uiScope.launch {
                        // In Kotlin, the return@label syntax is used for specifying which
                        //   function among several nested ones this statement returns from.
                        //   In this case, we are specifying to return from launch(), not
                        //   the lambda.
                        val oldNight = tonight.value ?: return@launch
                        tonight.value = null

                        // Update the night in the database to add the end time.
                        oldNight.endTimeMilli = System.currentTimeMillis()
                        update(oldNight)

                        // Set state to navigate to the SleepQualityFragment.
                        _navigateToSleepQuality.value = oldNight
                }
        }

        fun onClear() {
                uiScope.launch {
                        clear()
                        tonight.value = null
                        _showSnackBarEvent.value = true
                }
        }
        private suspend fun getTonightFromDatabase(): SleepNight? {
                return withContext(Dispatchers.IO) {
                        var night = database.getTonight()
                        if (night?.endTimeMilli != night?.endTimeMilli) {
                                night = null
                        }
                        night
                }
        }

        private suspend fun insert(night: SleepNight) {
                withContext(Dispatchers.IO) {
                        database.insert(night)
                }
        }

        private suspend fun update(night: SleepNight) {
                withContext(Dispatchers.IO) {
                        database.update(night)
                }
        }

        suspend fun clear() {
                withContext(Dispatchers.IO) {
                        database.clear()
                }
        }

        override fun onCleared() {
                super.onCleared()
                // When this viewModel is destroyed, onCleared() is called, and with this,
                //  all coroutines created in this viewModel are also finished.
                viewModelJob.cancel()
        }
}

