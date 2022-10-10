package com.serjlaren.sloom.data.domain.words

fun word(block: (Word.Builder.() -> Unit)? = null) = Word.Builder().apply { block?.invoke(this) }.build()

data class Word(
    val word: String,
    val topic: WordTopic,
) {
    class Builder {
        var word = ""
        var topic = WordTopic.All

        fun build() = Word(word = word, topic = topic)
    }
}
