package com.serjlaren.sloom.data.domain.words

@Suppress("MagicNumber")
enum class WordTopic(val id: Int) {
    All(0),
    Animal(1),
    Action(2),
    Bible(3);

    companion object {
        fun fromId(id: Int) = values().firstOrNull { it.id == id } ?: All
        fun fromIndex(index: Int) = values().firstOrNull { it.ordinal == index } ?: All
    }
}
