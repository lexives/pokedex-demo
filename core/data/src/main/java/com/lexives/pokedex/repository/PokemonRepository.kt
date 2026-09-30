package com.lexives.pokedex.repository

import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.dto.PokemonListResponse
import com.lexives.pokedex.extension.toDataState
import com.lexives.pokedex.service.PokeApiService
import com.lexives.pokedex.state.DataState
import com.lexives.pokedex.state.DataState.Error
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
    private val _pokemonFlow = MutableSharedFlow<List<DataState<Pokemon>>>()
    val pokemonFlow = _pokemonFlow.asSharedFlow()

    private var currentOffset = 0
    private var hasMorePages = true

    /**
     * Fetches the next page of Pokémon data from the API.
     *
     * @param pageSize The number of Pokémon to fetch. Default is 20.
     * @return A [DataState] object containing a list of [Pokemon] objects wrapped in their own [DataState].
     */
    suspend fun getNextPokemonPage(pageSize: Int = 20): DataState<List<DataState<Pokemon>>> {
        return if (!hasMorePages) {
            Success(emptyList())
        } else {
            // First call the /pokemon endpoint to get a list of pokemon names/urls
            val result = pokeApiService.getPokemonList(
                limit = pageSize,
                offset = currentOffset
            ).toDataState(
                mapper = { response: PokemonListResponse ->
                    // Then, for each result in the list, call the /pokemon/{name} endpoint to get
                    // the rest of the data
                    supervisorScope {
                        response.results.mapIndexed { i, result ->
                            async {
                                result.name?.let { name ->
                                    getPokemonByName(name)
                                } ?: Error("Could not fetch Pokemon data for element $i because " +
                                        "name is null")
                            }
                        }.awaitAll()
                    }
                },
                errorMessage = { response ->
                    "Could not convert PokemonListResponse to PokemonList object: ${response.body()}"
                }
            )

            if (result is Success) {
                currentOffset += pageSize
                hasMorePages = result.data.size < pageSize
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
    suspend fun getPokemonByName(name: String): DataState<Pokemon> {
        return pokeApiService
            .getPokemonByName(name)
            .toDataState(
                mapper =  {
                    Pokemon.fromResponse(it)
                        ?: throw Exception("Could not convert PokemonResponse to Pokemon object: $it")
                },
                errorMessage = { response ->
                    "Could not convert PokemonResponse to Pokemon object: ${response.body()}"
                }
            )
    }
}
