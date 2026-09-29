package com.lexives.pokedex

data class PokemonResponse(
    val id: Int? = null,
    val name: String? = null,
    val sprites: PokemonSpritesResponse? = null,
    val types: List<PokemonTypesResponse> = listOf()
)

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

data class PokemonTypesResponse(
    val slot: Int? = null,
    val type: PokemonTypeResponse
)

data class PokemonTypeResponse(
    val id: Int? = null,
    val name: String? = null
)
