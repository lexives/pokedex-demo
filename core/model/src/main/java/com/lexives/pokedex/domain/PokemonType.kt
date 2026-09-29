package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonTypeResponse

data class PokemonType(
    val id: Int,
    val name: String
) {
    companion object {
        const val UNKNOWN = "Unknown"

        /** Returns null if there is no valid id in the response. */
        fun fromResponse(response: PokemonTypeResponse): PokemonType? {
            return PokemonType(
                id = response.id ?: return null,
                name = response.name ?: UNKNOWN
            )
        }
    }
}
