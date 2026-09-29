package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonResponse

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val firstType: PokemonType,
    val secondType: PokemonType? = null
) {
    companion object {
        /** Returns null if there is no valid id, name, or type in the response. */
        fun fromResponse(response: PokemonResponse): Pokemon? {
            return Pokemon(
                id = response.id ?: return null,
                name = response.name?.takeIf { it.isNotBlank() } ?: return null,
                sprites = response.sprites?.let {
                    PokemonSprites.fromResponse(it)
                } ?: PokemonSprites(),
                firstType = response.types.getOrNull(0)?.let {
                    PokemonType.fromResponse(it.type)
                } ?: return null,
                secondType = response.types.getOrNull(1)?.let {
                    PokemonType.fromResponse(it.type)
                }
            )
        }
    }
}
