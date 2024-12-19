package com.golyv.network.mapper

import com.golyv.network.model.ResultState

fun <T : Any, R : Any> ResultState<T>.map(
    transformer: (value: T) -> R
): ResultState<R> {
    return if (this is ResultState.Success<T>) {
        ResultState.Success(transformer(this.body))
    } else this as ResultState<R>
}