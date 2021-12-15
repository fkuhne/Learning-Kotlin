package com.example.roomwordsample.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * To make the Word class meaningful to a Room database, you need to create an association between
 *    the class and the database using Kotlin annotations. You will use specific annotations to
 *    identify how each part of this class relates to an entry in the database. Room uses this extra
 *    information to generate code.
 */

@Entity(tableName = "word_table") // Each @Entity class represents a SQLite table.
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)
