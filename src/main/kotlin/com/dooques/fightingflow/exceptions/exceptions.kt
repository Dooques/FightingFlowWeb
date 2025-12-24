package com.dooques.fightingflow.exceptions

class FightingFlowExceptions {

    /*
    ---------------------------
    Open Exceptions
    ---------------------------
     */

    open class InvalidItemException(
        type: String,
        id: Any,
        problems: Map<String, String>
        ) : RuntimeException("Invalid item $type for id $id with these problems: $problems")

    open class NoItemFoundException(
        private val type: String,
        private val id: Long
    ) : RuntimeException("No $type with ID $id could not be found")

    open class NoItemsFoundException(type: String) : RuntimeException("No ${type}s found")

    open class ItemAlreadyExists(type: String) : RuntimeException("$type already exists")

    /*
    ---------------------------
    Typed Exception Classes
    ---------------------------
     */

    class Combo {

        class InvalidComboException(
            private val id: Long,
            private val problems: Map<String, String>,
        ) : InvalidItemException("combo", id, problems)

        class NoComboFoundException(id: Long) :
            NoItemFoundException("combo", id)

        class NoCombosFoundException : NoItemsFoundException("combo")

        class ComboAlreadyExists : ItemAlreadyExists("combo")
    }

    class Move {
        class InvalidMoveException(
            private val id: Long,
            private val problems: Map<String, String>,
        ) : InvalidItemException("move", id, problems)

        class NoMoveFoundException(id: Long) :
            NoItemFoundException("move", id)

        class NoMovesFoundException : NoItemsFoundException("move")

        class MoveAlreadyExistsException : ItemAlreadyExists("move")
    }

    class Character {
        class InvalidCharacterException(
            private val id: String,
            private val problems: Map<String, String>,
        ) : InvalidItemException("character", id, problems)

        class NoCharacterFoundException(id: Long) :
            NoItemFoundException("character", id)

        class NoCharactersFoundException : NoItemsFoundException("character")

        class CharacterAlreadyExists : ItemAlreadyExists("character")
    }

    class User {
        class InvalidUserException(
            private val id: Long,
            private val problems: Map<String, String>,
        ) : InvalidItemException("user", id, problems)

        class NoUserFoundException(id: Long) :
            NoItemFoundException("user", id)

        class NoCombosFoundException : NoItemsFoundException("user")

        class UserAlreadyExists : ItemAlreadyExists("user")
    }
}