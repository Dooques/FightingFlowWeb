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
        problems: Map<String, Any>
        ) : RuntimeException("Invalid item $type for id $id with these problems: $problems")

    open class NoItemFoundException(
        type: String, id: Long
    ) : RuntimeException("No $type with ID $id could be found")

    open class NoItemFoundByNameException(
        type: String, name: String
    ) : RuntimeException("No $type called $name could be found")

    open class NoItemsFoundException(type: String) :
        RuntimeException("No ${type}s found")

    open class ItemAlreadyExists(type: String) :
        RuntimeException("$type already exists")

    open class ItemFunctionFailedException(type: String, function: String, message: String) :
        RuntimeException("$type $function has failed with exception: $message")

    open class InvalidIdException() : RuntimeException("Invalid ID")
    /*
    ---------------------------
    Typed Exception Classes
    ---------------------------
     */

    class Combo {

        class InvalidComboException(
            id: Long, problems: Map<String, Any>,
        ) : InvalidItemException("combo", id, problems)

        class NoComboFoundException(id: Long) :
            NoItemFoundException("combo", id)

        class NoCombosFoundByTitleException(title: String) :
            NoItemFoundByNameException("combo", title)

        class NoCombosFoundException :
            NoItemsFoundException("combo")

        class ComboAlreadyExistsException :
            ItemAlreadyExists("combo")

        // Function Exceptions

        class PostFunctionFailedException(message: String) :
            ItemFunctionFailedException("Combo", "Post", message)

        class PutFunctionFailedException(message: String) :
            ItemFunctionFailedException("Combo", "Put", message)

        class DeleteFunctionFailedException(message: String) :
            ItemFunctionFailedException("Combo", "Delete", message)
    }

    class Move {
        class InvalidMoveException(
            id: Long, problems: Map<String, Any>,
        ) : InvalidItemException("move", id, problems)

        class NoMoveFoundException(id: Long) : NoItemFoundException("move", id)

        class NoMovesFoundException : NoItemsFoundException("move")

        class MoveAlreadyExistsException : ItemAlreadyExists("move")

        // Function Exceptions

        class PostFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Post", message)

        class PutFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Put", message)

        class DeleteFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Delete", message)
    }

    class Character {
        class InvalidCharacterException(
            id: String, problems: Map<String, Any>,
        ) : InvalidItemException("character", id, problems)

        class NoCharacterFoundException(id: Long) : NoItemFoundException("character", id)

        class NoCharacterFoundByNameException(name: String) :
            NoItemFoundByNameException("character", name)

        class NoCharactersFoundException : NoItemsFoundException("character")

        class CharacterAlreadyExists : ItemAlreadyExists("character")

        // Function Exceptions

        class PostFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Post", message)

        class PutFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Put", message)

        class DeleteFunctionFailedException(message: String) :
            ItemFunctionFailedException("Move", "Delete", message)
    }

    class User {
        class InvalidUserException(
            id: Long, problems: Map<String, Any>,
        ) : InvalidItemException("user", id, problems)

        class NoUserFoundException(id: Long) : NoItemFoundException("user", id)

        class NoUserFoundByNameException(name: String) : NoItemFoundByNameException("user", name)

        class NoCombosFoundException : NoItemsFoundException("user")

        class UserAlreadyExists : ItemAlreadyExists("user")

        // Function Exceptions
        class PostFunctionFailedException(message: String = "") :
            ItemFunctionFailedException("User", "Post", message)

        class PutFunctionFailedException(message: String) :
            ItemFunctionFailedException("User", "Put", message)

        class DeleteFunctionFailedException(message: String) :
            ItemFunctionFailedException("User", "Delete", message)
    }
}