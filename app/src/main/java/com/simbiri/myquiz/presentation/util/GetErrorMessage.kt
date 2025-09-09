package com.simbiri.myquiz.presentation.util

import com.simbiri.myquiz.domain.util.DataError

fun DataError.getErrorMessage(): String{
    return when(this){
        DataError.NoInternetConnection -> "No internet connection"
        DataError.RequestTimeout -> "Request timeout. Try again later"
        DataError.SerializationError -> "Failed to deserialize data, try again"
        DataError.ServerError -> "Server error occurred. Try again later"
        DataError.TooManyRequests -> "Too many requests. Limit your requests"
        is DataError.Unknown -> "An unknown error occurred. ${this.errorMessage}"
    }
}