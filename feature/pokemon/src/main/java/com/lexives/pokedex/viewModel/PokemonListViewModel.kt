package com.lexives.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.repository.PokemonRepository
import com.lexives.pokedex.state.DataState
import com.lexives.pokedex.state.DataState.Error
import com.lexives.pokedex.state.DataState.Loading
import com.lexives.pokedex.state.DataState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val viewState = MutableStateFlow<ViewState>(ViewState())

    companion object {
        data class ViewState(
            // The overall state of the Pokémon list. Contains a list of DataState objects
            // representing the state of each Pokémon in the list.
            val pokemonListState: DataState<List<DataState<Pokemon>>> = Loading(),

            // A map of indices to the names of the Pokémon that are currently getting fetched.
            // Indeces correspond to the postion in pokemonListState.
            val nextPage: Map<Int, String?> = emptyMap()
        )
    }

    init {
        fetchMorePokemon()
    }

    fun fetchMorePokemon() {
        // Prevent duplicate requests while a page is already loading
        if (viewState.value.nextPage.isNotEmpty()) return

        viewModelScope.launch {
            val result = pokemonRepository.getNextPokemonPage()
            if (result is Success) {
                // On a success, add Loading states to the list of Pokémon states while we fetch
                // more data
                val loadingStates = result.data.map { Loading<Pokemon>() }
                val currentListState = viewState.value.pokemonListState
                val currentList = if (currentListState is Success) { currentListState.data } else emptyList()
                val newPokemonList = currentList + loadingStates

                // Update the view state with the new list of Pokémon states and the next page
                // that's waiting to be fetched
                viewState.value = viewState.value.copy(
                    pokemonListState = Success(newPokemonList),
                    nextPage = result.data.mapIndexed { i, name ->
                        (i + currentList.size) to name
                    }.toMap()
                )
                fetchPokemonData()
            } else if (result is Error) {
                // On an error, update the view state with the error if we are loading the first
                // batch of Pokémon, but leave it as-is if it's already in a success state
                if (viewState.value.pokemonListState !is Success) {
                    viewState.value = viewState.value.copy(
                        pokemonListState = Error(result.message)
                    )
                }
            }
        }
    }

    private fun fetchPokemonData() {
        viewModelScope.launch {
            supervisorScope {
                // Fetch Pokémon data concurrently
                val results: Map<Int, DataState<Pokemon>> = viewState.value.nextPage.map { entry ->
                    async {
                        entry.key to pokemonRepository.getPokemonByName(entry.value)
                    }
                }.awaitAll().toMap()

                // Update the view state with the fetched Pokémon data and clear out nextPage
                val newPokemonList = when (val currentState = viewState.value.pokemonListState) {
                    is Success -> {
                        // Replace placeholder Loading states with actual results
                        currentState.data.toMutableList().apply {
                            results.forEach { (index, result) ->
                                if (index in indices) {
                                    this[index] = result
                                }
                            }
                        }.toList()
                    }
                    else -> {
                        // Technically we should always be in a success state by this point so this
                        // shouldn't trigger
                        results.map { it.value }
                    }
                }
                viewState.value = viewState.value.copy(
                    pokemonListState = Success(newPokemonList),
                    nextPage = emptyMap()
                )
            }
        }
    }
}
