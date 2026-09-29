package com.lexives.pokedex.extension

import com.lexives.pokedex.state.DataState.Error
import com.lexives.pokedex.state.DataState.Success
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith

class ResponseExtensionsTest {
    @Test
    fun toDataState_returns_success_if_response_and_mapper_are_successful() {
        val expectedResult = Success("1")
        val result = Response.success(1).toDataState(
            mapper = { it.toString() },
            errorMessage = { "Error message" }
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun toDataState_returns_error_if_response_is_not_successful() {
        val expectedResult = Error<String>("Error message")
        val result = Response.error<Int>(500, "Error from upstream".toResponseBody()).toDataState(
            mapper = { it.toString() },
            errorMessage = { "Error message" }
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun toDataState_returns_error_if_response_body_is_null() {
        val expectedResult = Error<String>("Error message")
        val result = Response.success(null).toDataState(
            mapper = { it.toString() },
            errorMessage = { "Error message" }
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun toDataState_returns_error_if_mapper_throws_an_exception() {
        val expectedResult = Error<String>("Mapper error")
        val result = Response.success(1).toDataState(
            mapper = { throw Exception("Mapper error") },
            errorMessage = { "Error message" }
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun toDataState_throws_cancellation_exception() {
        assertFailsWith<CancellationException> {
            Response.success(1).toDataState(
                mapper = { throw CancellationException() },
                errorMessage = { "Error message" }
            )
        }
    }
}
