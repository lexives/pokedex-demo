package com.lexives.pokedex

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonListResultResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class PokemonListResultResponse(
    val name: String? = null,
    val url: String? = null
)
