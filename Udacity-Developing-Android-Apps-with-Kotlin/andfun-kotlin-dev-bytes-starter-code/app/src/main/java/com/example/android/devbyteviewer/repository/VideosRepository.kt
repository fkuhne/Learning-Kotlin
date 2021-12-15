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
 *
 */

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/* Repositories are like any regular classes. We don't need to extend anything or use annotations.
   They are responsible for providing a simple API to our datasources.
 */

/**
 * Repository for fetching devbyte videos from the network and storing them on disk.
 *   By taking the database object as a constructor parameter, we don't need to keep a reference
 *   to Android context in our repository, potentially causing leaks. This way you won’t have
 *   any dependencies on Context in your repository (so called dependency injection).
 */
class VideosRepository(private val database: VideosDatabase) {

    /**
     * A playlist of videos that can be shown on the screen.
     */
    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        databaseVideos -> databaseVideos.asDomainModel()
    }
    
    /**
     * Refresh the videos stored in the offline cache.
     * This function uses the IO Dispatcher to ensure the database insert database operation
     *   happens on the IO dispatcher. By switching to IO dispatcher using 'withContext' this
     *   function is now safe to call from any thread including the Main thread.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}