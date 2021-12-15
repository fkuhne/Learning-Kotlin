package com.example.roomwordsample.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomwordsample.repository.WordDao
import com.example.roomwordsample.repository.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * What is a Room database?
 *   Room is a database layer on top of an SQLite database.
 *   Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 *   Room uses the DAO to issue queries to its database.
 *   By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main
 *     thread. When Room queries return Flow, the queries are automatically run asynchronously on a
 *     background thread.
 *   Room provides compile-time checks of SQLite statements.
 */

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        /* Singleton prevents multiple instances of datbase
         *   opening at the same time. */
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            /* If INSTANCE is not null, it means that the database has already been created, then
             *   return it. OTherwise, create the database. */
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_data  base"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.wordDao()) }
            }
        }
        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            // Add some initial samples...
            wordDao.insert(Word("Hello"))
            wordDao.insert(Word("World"))
            wordDao.insert(Word("Paris"))
            wordDao.insert(Word("London"))
            wordDao.insert(Word("Berlin"))
        }
    }
}

