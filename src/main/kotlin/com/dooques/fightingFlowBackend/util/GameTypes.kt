package com.dooques.fightingFlowBackend.util

enum class Game(
    val title: String
) {
    Tekken8("Tekken 8"),
    StreetFighter6("Street Fighter 6"),
    MortalKombat11("Mortal Kombat 11")
}

enum class SF6ControlType(val type: Long) {
    Modern(0),
    Classic(1),
    Invalid(2)
}

enum class ControlType(val title: String) {
    ArcSys("ArcSys"),
    MortalKombat("Mortal Kombat"),
    NumpadNotation("Numpad Notation"),
    StreetFighterC("Street Fighter Classic"),
    StreetFighterM("Street Fighter Modern"),
    TagFighter("Tag Fighter"),
    Tekken("Tekken"),
    VirtuaFighter("Virtua Fighter"),
}

enum class ConsoleType {
    STANDARD, XBOX, PLAYSTATION, NINTENDO
}