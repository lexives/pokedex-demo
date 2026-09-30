package com.lexives.pokedex

import androidx.lifecycle.ViewModel
import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.repository.PokemonRepository
import com.lexives.pokedex.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.lexives.pokedex.state.DataState.Success
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val viewState = MutableStateFlow<ViewState>(ViewState())

    fun fetchMorePokemon() {
        viewModelScope.launch {
            val result = pokemonRepository.getNextPokemonPage()

            if (result is Success) {
                when (viewState.value.pokemonListState) {
                    is Success -> {
                        val currentListState = viewState.value.pokemonListState
                                as Success<List<DataState<Pokemon>>>

                        viewState.value = viewState.value.copy(
                            pokemonListState = Success(
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
