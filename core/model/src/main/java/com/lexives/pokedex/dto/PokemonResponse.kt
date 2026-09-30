package com.lexives.pokedex.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val id: Int? = null,
    val name: String? = null,
    val sprites: PokemonSpritesResponse? = null,
    val types: List<PokemonTypesResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class PokemonSpritesResponse(
    val frontDefault: String? = null,
    val frontShiny: String? = null,
    val frontFemale: String? = null,
    val frontShinyFemale: String? = null,
    val backDefault: String? = null,
    val backShiny: String? = null,
    val backFemale: String? = null,
    val backShinyFemale: String? = null,
)

@JsonClass(generateAdapter = true)
data class PokemonTypesResponse(
    val slot: Int? = null,
    val type: PokemonTypeResponse
)

@JsonClass(generateAdapter = true)
data class PokemonTypeResponse(
    val name: String? = null
)
