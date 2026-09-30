package com.lexives.pokedex.repository

import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.dto.PokemonListResponse
import com.lexives.pokedex.extension.toDataState
import com.lexives.pokedex.service.PokeApiService
import com.lexives.pokedex.state.DataState
import com.lexives.pokedex.state.DataState.Error
import com.lexives.pokedex.state.DataState.Loading
import com.lexives.pokedex.state.DataState.Success
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokeApiService: PokeApiService
) {
    private var currentOffset = 0
    private var hasMorePages = true

    /**
     * Fetches the next page of Pokémon data from the API and maps it to a list of names.
     *
     * @param pageSize The number of Pokémon to fetch. Default is 20.
     * @return A [DataState] object containing a list of names.
     */
    suspend fun getNextPokemonPage(pageSize: Int = 20): DataState<List<String?>> {
        return if (!hasMorePages) {
            Success(emptyList())
        } else {
            val result = pokeApiService.getPokemonList(
                limit = pageSize,
                offset = currentOffset
            ).toDataState(
                mapper = { response: PokemonListResponse ->
                    response.results.map { it.name }
                },
                errorMessage = { response ->
                    "Could not extract names from PokemonListResponse object: ${response.body()}"
                }
            )

            if (result is Success) {
                currentOffset += pageSize
                hasMorePages = result.data.size >= pageSize
            }

            result
        }
    }

    /**
     * Fetches Pokémon data by name from the API.
     *
     * @param name The name of the Pokémon, which is a unique identifier.
     * @return A [DataState] object containing a [Pokemon].
     */
    suspend fun getPokemonByName(name: String?): DataState<Pokemon> {
        return name?.let {
            pokeApiService
                .getPokemonByName(name)
                .toDataState(
                    mapper = {
                        Pokemon.fromResponse(it)
                            ?: throw Exception("Could not convert PokemonResponse to Pokemon object: $it")
                    },
                    errorMessage = { response ->
                        "Could not convert PokemonResponse to Pokemon object: ${response.body()}"
                    }
                )
        } ?: Error("Could not fetch Pokémon data because name is null")
    }
}
