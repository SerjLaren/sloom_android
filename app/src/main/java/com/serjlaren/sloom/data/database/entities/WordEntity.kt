package com.serjlaren.sloom.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serjlaren.sloom.data.database.WordsDatabase

@Entity(tableName = WordsDatabase.wordEntitiesName)
data class WordEntity(
    @PrimaryKey
    val id: Long,
    val word: String,
    val wordTopic: Int,
)
