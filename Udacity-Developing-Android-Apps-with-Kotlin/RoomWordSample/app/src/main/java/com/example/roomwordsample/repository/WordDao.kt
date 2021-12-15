package com.example.roomwordsample.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomwordsample.repository.model.Word
import kotlinx.coroutines.flow.Flow

/**
 * In the DAO (data access object), you specify SQL queries and associate them with method calls.
 *   The compiler checks the SQL and generates queries from convenience annotations for common
 *   queries, such as @Insert. Room uses the DAO to create a clean API for your code.
 */
@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE from word_table")
    suspend fun deleteAll()
}