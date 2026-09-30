package com.lexives.pokedex.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lexives.pokedex.domain.Pokemon
import com.lexives.pokedex.domain.PokemonSprites
import com.lexives.pokedex.domain.PokemonType

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF5A6F85))
            .padding(16.dp)
    ) {
        // TODO: image
        Text(
            text = pokemon.name, // TODO: title case
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        TypeChip(type = pokemon.firstType)
        pokemon.secondType?.let {
            TypeChip(type = it)
        }
    }
}

@Composable
fun PokemonListItemLoading() {
    // TODO
}

@Composable
fun PokemonListItemError() {
    // TODO
}

/** Previews **/

@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokemonListItem(
        pokemon = Pokemon(
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
    )
}
