package com.simbiri.myquiz.domain.util

// wrapper interface for handling errors and success responses when we retrieve domain data
// from either the database or the ktor server
sealed interface ResultType <out D, out E: Error> {
    // success output value D can be of any datatype
    data class Success<out D>(val data: D) : ResultType<D, Nothing>
    data class Failure<out E: Error>(val error: E) : ResultType<Nothing, E>
}

inline fun <T, E: Error>ResultType<T, E>.onSuccess(action: (T) -> Unit): ResultType<T, E> {
    if (this is ResultType.Success) action(data)
    return this
}

inline fun <T, E: Error>ResultType<T, E>.onFailure(action: (E) -> Unit): ResultType<T, E> {
    if (this is ResultType.Failure) action(error)
    return this
}