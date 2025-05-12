package com.zanoapps.core.domain.util

// this should either wrap an successful code or an error during execution o a function
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.zanoapps.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}


inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

typealias EmptyResult<E> = Result<Unit, E>


/*Example*/
enum class PasswordValidationError : Error {
    TOO_SHORT,
    MISSING_ONE_DIGIT
}

fun validatePassword(password: String): Result<Unit, PasswordValidationError> {
    return Result.Error(PasswordValidationError.MISSING_ONE_DIGIT)
}

fun handleError(result: Result<Unit, PasswordValidationError>) {
    when (result) {
        is Result.Error -> {
            when (result.error) {
                PasswordValidationError.TOO_SHORT -> TODO()
                PasswordValidationError.MISSING_ONE_DIGIT -> TODO()
            }
        }

        is Result.Success -> {

        }
    }
}