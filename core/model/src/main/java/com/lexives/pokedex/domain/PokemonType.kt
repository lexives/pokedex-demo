package com.lexives.pokedex.domain

import com.lexives.pokedex.dto.PokemonTypeResponse

data class PokemonType(
    val id: Int,
    val name: String
) {
    fun getColorHex() : Long? {
        return when (name) {
            "normal" -> 0xFFAAAA99
            "fire" -> 0xFFFF4422
            "water" -> 0xFF3399FF
            "electric" -> 0xFFFFCC33
            "grass" -> 0xFF77CC55
            "ice" -> 0xFF66CCFF
            "fighting" -> 0xFFBB5544
            "poison" -> 0xFFAA5599
            "ground" -> 0xFFDDBB55
            "flying" -> 0xFF8899FF
            "psychic" -> 0xFFFF5599
            "bug" -> 0xFFAABB22
            "rock" -> 0xFFBBAA66
            "ghost" -> 0xFF6666BB
            "dragon" -> 0xFF7766EE
            "dark" -> 0xFF775544
            "steel" -> 0xFFAAAABB
            "fairy" -> 0xFFEE99EE
            else -> null
        }
    }

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
