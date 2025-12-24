package com.dooques.fightingflow.exceptions

class FightingFlowExceptions {

    class Combo {
        class InvalidComboException(
            private val id: Long,
            private val problems: MutableMap<String, Any>,
        ) : RuntimeException("Combo $id is invalid: Invalid fields: $problems")

        class ComboNotFoundException(private val id: Long) :
            RuntimeException("The combo with ID $id could not be found")

        class NoCombosFoundException : RuntimeException("No combos found")
    }

    class Move {
        class InvalidMoveException(
            private val id: Long,
            private val problems: Map<String, String>
        ) : RuntimeException("Move $id is invalid: Invalid fields: $problems")

        class MoveNotFoundException(private val id: Long) :
            RuntimeException("The move with ID $id could not be found")

        class NoMovesFoundException : RuntimeException("No moves found")
    }

    class Character {
        class InvalidCharacterException(
            private val name: String,
            private val problems: Map<String, String>
        ) : RuntimeException("Character $name is invalid: Invalid fields: $problems")

        class CharacterNotFoundException(private val identifier: String) :
            RuntimeException("The character with value $identifier could not be found")

        class NoCharactersFoundException : RuntimeException("No characters found")
    }

    class User {
        class InvalidUserException(
            private val id: Long,
            private val problems: Map<String, String>
        ) : RuntimeException("User $id is invalid: Invalid fields: $problems")

        class UserNotFoundException(private val id: Long) :
            RuntimeException("The user with ID $id could not be found")

        class NoUsersFoundException : RuntimeException("No users found")
    }
}