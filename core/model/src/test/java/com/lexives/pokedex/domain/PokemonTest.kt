package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonResponse
import com.lexives.pokedex.dto.PokemonTypeResponse
import com.lexives.pokedex.dto.PokemonTypesResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PokemonTest {
    val pokemonResponse = PokemonResponse(
        id = 1,
        name = "bulbasaur",
        sprites = null,
        types = listOf(
            PokemonTypesResponse(
                slot = 1,
                type = PokemonTypeResponse(
                    name = "grass"
                )
            ),
            PokemonTypesResponse(
                slot = 2,
                type = PokemonTypeResponse(
                    name = "poison"
                )
            )
        )
    )

    @Test
    fun fromResponse_returns_pokemon_object_when_response_is_valid() {
        val expectedResult = Pokemon(
            id = 1,
            name = "bulbasaur",
            sprites = PokemonSprites(),
            firstType = PokemonType(
                name = "grass"
            ),
            secondType = PokemonType(
                name = "poison"
            )
        )
        val result = Pokemon.fromResponse(pokemonResponse)
        assertEquals(expectedResult, result)
    }

    @Test
    fun fromResponse_returns_null_when_id_is_null() {
        val result = Pokemon.fromResponse(
            pokemonResponse.copy(id = null)
        )
        assertNull(result)
    }

    @Test
    fun fromResponse_returns_null_when_name_is_null() {
        val result = Pokemon.fromResponse(
            pokemonResponse.copy(name = null)
        )
        assertNull(result)
    }

    @Test
    fun fromResponse_returns_null_when_name_is_blank() {
        val result = Pokemon.fromResponse(
            pokemonResponse.copy(name = " ")
        )
        assertNull(result)
    }
}
