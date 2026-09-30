package com.lexives.pokedex.extension

import com.lexives.pokedex.state.DataState
import com.lexives.pokedex.state.DataState.Error
import com.lexives.pokedex.state.DataState.Success
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

/**
 * Converts a [Response] of type [T] to a [DataState] of type [R].
 *
 * @param mapper A function that maps the response body to an [R] object.
 * @param errorMessage A function that returns an error message for the given [Response].
 * @return A [DataState] object containing the mapped data or an error message.
 */
inline fun <T, R> Response<T>.toDataState(
    mapper: (T) -> R,
    errorMessage: (Response<T>) -> String,
): DataState<R> {
    if (!isSuccessful) return Error(errorMessage(this))
    val body = body() ?: return Error(errorMessage(this))

    val mapped = try {
        mapper(body)
    } catch (e: Exception) {
        // Since this can be run in suspend functions, we don't want to swallow cancellation exceptions
        if (e is CancellationException) throw e
        return Error(e.message ?: e.toString())
    }

    return Success(mapped)
}
