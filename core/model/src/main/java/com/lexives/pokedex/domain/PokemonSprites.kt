package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonSpritesResponse

data class PokemonSprites(
    val frontDefault: String? = null,
    val frontShiny: String? = null,
    val frontFemale: String? = null,
    val frontShinyFemale: String? = null,
    val backDefault: String? = null,
    val backShiny: String? = null,
    val backFemale: String? = null,
    val backShinyFemale: String? = null,
) {
    companion object {
        fun fromResponse(response: PokemonSpritesResponse): PokemonSprites {
            return PokemonSprites(
                frontDefault = response.frontDefault,
                frontShiny = response.frontShiny,
                frontFemale = response.frontFemale,
                frontShinyFemale = response.frontShinyFemale,
                backDefault = response.backDefault,
                backShiny = response.backShiny,
                backFemale = response.backFemale,
                backShinyFemale = response.backShinyFemale
            )
        }
    }
}
