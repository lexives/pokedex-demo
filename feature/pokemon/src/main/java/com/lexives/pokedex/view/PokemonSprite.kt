package com.lexives.pokedex.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.lexives.pokedex.domain.PokemonSprites

val SPRITE_SIZE = 72.dp

@Composable
fun PokemonSprite(
    sprites: PokemonSprites,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(SPRITE_SIZE)
            .clip(CircleShape)
            .background(Color.LightGray)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = CircleShape
            )
    ) {
        sprites.frontDefault?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier.size(SPRITE_SIZE)
            )
        } ?: Text(
            text = "?",
            color = Color.DarkGray,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Previews **/

@Preview(showBackground = true)
@Composable
fun PokemonSpritePreview() {
    PokemonSprite(
        sprites = PokemonSprites()
    )
}
