package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonTypeResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PokemonTypeTest {
    val pokemonTypeResponse = PokemonTypeResponse(
        name = "grass"
    )
    @Test
    fun fromResponse_returns_pokemon_type_object_when_response_is_valid() {
        val expectedResult = PokemonType(
            name = "grass"
        )
        val result = PokemonType.fromResponse(pokemonTypeResponse)
        assertEquals(expectedResult, result)
    }

    @Test
    fun fromResponse_sets_name_to_unknown_when_name_is_null() {
        val expectedResult = PokemonType(
            name = "Unknown"
        )
        val result = PokemonType.fromResponse(pokemonTypeResponse.copy(name = null))
        assertEquals(expectedResult, result)
    }
}
