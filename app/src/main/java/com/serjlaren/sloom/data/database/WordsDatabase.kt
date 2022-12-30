package com.serjlaren.sloom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serjlaren.sloom.data.database.dao.WordsDao
import com.serjlaren.sloom.data.database.entities.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {

    internal abstract fun wordsDao(): WordsDao

    companion object {
        const val databaseName = "sloom_words"
        const val wordEntitiesName = "words_entities"
    }
}
