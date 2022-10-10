package com.serjlaren.sloom.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.serjlaren.sloom.data.database.WordsDatabase
import com.serjlaren.sloom.data.database.entities.WordEntity

@Dao
interface WordsDao {
    @Query("SELECT * FROM ${WordsDatabase.wordEntitiesName} ORDER BY RANDOM() LIMIT :wordsCount")
    suspend fun getAllWords(wordsCount: Int): List<WordEntity>

    @Query("SELECT * FROM ${WordsDatabase.wordEntitiesName} WHERE wordTopic = :topic")
    suspend fun getTopicWords(topic: Int): List<WordEntity>
}
