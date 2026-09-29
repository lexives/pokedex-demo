package com.lexives.pokedex.state

sealed class DataState<T> {
    class Loading<T> : DataState<T>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()
}
