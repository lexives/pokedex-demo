package com.lexives.pokedex.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.domain.PokemonSprites
import com.lexives.pokedex.domain.PokemonType
import com.lexives.pokedex.state.DataState
import com.lexives.pokedex.state.DataState.Error
import com.lexives.pokedex.state.DataState.Loading
import com.lexives.pokedex.state.DataState.Success
import com.lexives.pokedex.viewModel.PokemonListViewModel
import com.lexives.pokedex.viewModel.PokemonListViewModel.Companion.ViewState

@Composable
fun PokemonListScreen() {
    val viewModel = hiltViewModel<PokemonListViewModel>()
    val viewState = viewModel.viewState.collectAsState()

    PokemonListScreenContent(viewState.value)
}

@Composable
fun PokemonListScreenContent(
    viewState: ViewState
) {
    when (viewState.pokemonListState) {
        is Loading -> {
            PokemonListSplashScreen()
        }
        is Error -> {
            PokemonListErrorScreen()
        }
        is Success -> {
            PokemonList(viewState.pokemonListState.data)
        }
    }
}

@Composable
fun PokemonListSplashScreen() {
    // TODO
}

@Composable
fun PokemonListErrorScreen() {
    // TODO
}

@Composable
fun PokemonList(
    pokemonList: List<DataState<Pokemon>>
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        pokemonList.forEachIndexed { i, pokemonState ->
            item(key = i) {
                when (pokemonState) {
                    is Loading -> {
                        PokemonListItemLoading()
                    }

                    is Error -> {
                        PokemonListItemError()
                    }

                    is Success -> {
                        PokemonListItem(
                            pokemon = pokemonState.data
                        )
                    }
                }
            }
        }
    }
}

/** Previews **/

@Preview(showBackground = true)
@Composable
fun PokemonListPreview() {
    PokemonList(
        pokemonList = listOf(
            Success(
                Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    sprites = PokemonSprites(),
                    firstType = PokemonType(
                        id = 12,
                        name = "grass"
                    ),
                    secondType = PokemonType(
                        id = 4,
                        name = "poison"
                    )
                )
            ),
            Success(
                Pokemon(
                    id = 4,
                    name = "charmander",
                    sprites = PokemonSprites(),
                    firstType = PokemonType(
                        id = 10,
                        name = "fire"
                    )
                )
            ),
            Success(
                Pokemon(
                    id = 7,
                    name = "squirtle",
                    sprites = PokemonSprites(),
                    firstType = PokemonType(
                        id = 11,
                        name = "water"
                    )
                )
            )
        )
    )
}
