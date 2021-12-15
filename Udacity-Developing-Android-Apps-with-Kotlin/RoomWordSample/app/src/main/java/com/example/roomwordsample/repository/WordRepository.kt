package com.example.roomwordsample.repository

import androidx.annotation.WorkerThread
import com.example.roomwordsample.repository.model.Word
import kotlinx.coroutines.flow.Flow

/**
 * A repository class abstracts access to multiple data sources. The repository is not part of the
 *   Architecture Components libraries, but is a suggested best practice for code separation and
 *   architecture. A Repository class provides a clean API for data access to the rest of the
 *   application.
 *
 * A Repository manages queries and allows you to use multiple backends. In the most common
 *   example, the Repository implements the logic for deciding whether to fetch data from a network
 *   or use results cached in a local database.
 */

class WordRepository(private val wordDao: WordDao) {

    // Observed flow will notify the observer when data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}