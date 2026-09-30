package com.lexives.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.repository.PokemonRepository
import com.lexives.pokedex.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val viewState = MutableStateFlow<ViewState>(ViewState())

    init {
        fetchMorePokemon()
    }

    fun fetchMorePokemon() {
        viewModelScope.launch {
            val result = pokemonRepository.getNextPokemonPage()

            if (result is DataState.Success) {
                when (viewState.value.pokemonListState) {
                    is DataState.Success -> {
                        val currentListState = viewState.value.pokemonListState
                                as DataState.Success<List<DataState<Pokemon>>>

                        viewState.value = viewState.value.copy(
                            pokemonListState = DataState.Success(
                                currentListState.data + result.data
                            )
                        )
                    }
                    else -> {
                        viewState.value = viewState.value.copy(
                            pokemonListState = result
                        )
                    }
                }
            }
        }
    }

    companion object {
        data class ViewState(
            val pokemonListState: DataState<List<DataState<Pokemon>>> = DataState.Loading()
        )
    }
}