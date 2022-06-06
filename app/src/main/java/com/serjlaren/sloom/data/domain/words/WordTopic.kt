package com.serjlaren.sloom.data.domain.words

enum class WordTopic(private val id: Int) {
    All(0),
    Animal(1),
    Person(2),
    Profession(3),
    Action(4),
    Clothes(5),
    Transport(6),
    Bible(7);

    companion object {
        fun fromId(id: Int) = values().firstOrNull { it.id == id } ?: All
    }
}