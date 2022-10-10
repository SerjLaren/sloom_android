package com.serjlaren.sloom.services

import com.serjlaren.sloom.data.database.dao.WordsDao
import com.serjlaren.sloom.data.database.entities.WordEntity
import com.serjlaren.sloom.data.domain.words.Word
import com.serjlaren.sloom.data.domain.words.WordTopic
import com.serjlaren.sloom.data.domain.words.word
import com.serjlaren.sloom.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseService @Inject constructor(
    private val wordsDao: WordsDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getTopicsWords(topics: List<WordTopic>, wordsCount: Int): List<Word> =
        withContext(ioDispatcher) {
            val result = mutableListOf<WordEntity>()
            topics.forEach { wordTopic ->
                if (wordTopic == WordTopic.All) {
                    result.addAll(wordsDao.getAllWords(wordsCount))
                }
                else {
                    result.addAll(wordsDao.getTopicWords(wordTopic.id))
                }
            }
            return@withContext result.shuffled().subList(0, wordsCount).map { wordEntity ->
                word {
                    word = wordEntity.word
                    topic = WordTopic.fromId(wordEntity.wordTopic)
                }
            }
        }
}
