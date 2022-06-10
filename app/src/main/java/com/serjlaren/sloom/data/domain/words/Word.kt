package com.serjlaren.sloom.data.domain.words

data class Word(
    val word: String,
    val level: WordLevel,
    val topic: WordTopic,
) {
    companion object {
        fun defaultWord() = Word("", WordLevel.All, WordTopic.All)
    }
}