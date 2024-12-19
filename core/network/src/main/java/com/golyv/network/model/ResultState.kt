package com.golyv.network.model

import okhttp3.Headers

sealed class ResultState<out T : Any> {
    data class Success<T : Any>(val body: T, val headers: Headers? = null) : ResultState<T>()
    data class Error<T : Any>(val errorMessage: String) :
        ResultState<T>()
}

