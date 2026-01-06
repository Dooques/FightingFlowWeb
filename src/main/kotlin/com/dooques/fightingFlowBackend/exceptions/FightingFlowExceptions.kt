package com.dooques.fightingFlowBackend.exceptions

class FightingFlowExceptions {

    open class InvalidItemException(
        type: String,
        id: Any,
        problems: Map<String, Any>
        ) : RuntimeException("Invalid item $type for id $id with these problems: $problems")

    open class NoItemFoundException(
        type: String, id: Any
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

    open class InvalidIdException : RuntimeException("Invalid ID")
}