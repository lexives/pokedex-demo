package com.lexives.pokedex.dto

import com.squareup.moshi.Json
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
    @Json(name = "front_default") val frontDefault: String? = null,
    @Json(name = "front_shiny") val frontShiny: String? = null,
    @Json(name = "front_female") val frontFemale: String? = null,
    @Json(name = "front_shiny_female") val frontShinyFemale: String? = null,
    @Json(name = "back_default") val backDefault: String? = null,
    @Json(name = "back_shiny") val backShiny: String? = null,
    @Json(name = "back_female") val backFemale: String? = null,
    @Json(name = "back_shiny_female") val backShinyFemale: String? = null,
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
