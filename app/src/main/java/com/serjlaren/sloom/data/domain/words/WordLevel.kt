package com.serjlaren.sloom.data.domain.words

@Suppress("MagicNumber")
enum class WordLevel(private val id: Int) {
    All(0),
    Easy(1),
    Medium(2),
    Hard(3);

    companion object {
        fun fromId(id: Int) = values().firstOrNull { it.id == id } ?: All
    }
}
