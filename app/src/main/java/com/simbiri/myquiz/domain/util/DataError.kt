package com.simbiri.myquiz.domain.util

sealed interface DataError : Error {
    data object RequestTimeout : DataError
    data object TooManyRequests : DataError
    data object NoInternetConnection : DataError
    data object ServerError : DataError
    data object SerializationError: DataError
    data class Unknown (val errorMessage: String? =  null): DataError
}