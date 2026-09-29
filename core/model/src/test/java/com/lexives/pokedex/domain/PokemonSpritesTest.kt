package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonSpritesResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonSpritesTest {
    @Test
    fun fromResponse_returns_pokemon_sprites_object_when_response_has_values() {
        val expectedResult = PokemonSprites(
            frontDefault = "frontDefault",
            frontShiny = "frontShiny",
            frontFemale = "frontFemale",
            frontShinyFemale = "frontShinyFemale",
            backDefault = "backDefault",
            backShiny = "backShiny",
            backFemale = "backFemale",
            backShinyFemale = "backShinyFemale"
        )
        val result = PokemonSprites.fromResponse(
            PokemonSpritesResponse(
                frontDefault = "frontDefault",
                frontShiny = "frontShiny",
                frontFemale = "frontFemale",
                frontShinyFemale = "frontShinyFemale",
                backDefault = "backDefault",
                backShiny = "backShiny",
                backFemale = "backFemale",
                backShinyFemale = "backShinyFemale"
            )
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun fromResponse_returns_pokemon_sprites_object_when_response_has_nulls() {
        val expectedResult = PokemonSprites()
        val result = PokemonSprites.fromResponse(PokemonSpritesResponse())
        assertEquals(expectedResult, result)
    }
}
