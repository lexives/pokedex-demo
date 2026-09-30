package com.lexives.pokedex.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lexives.pokedex.domain.PokemonType

@Composable
fun TypeChip(
    type: PokemonType,
    modifier: Modifier = Modifier
) {
    Text(
        text = type.name.uppercase(),
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                type.getColorHex()?.let {
                    Color(it)
                } ?: Color.LightGray
            )
            .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp)) // TODO: dynamic colors
            .padding(6.dp)
    )
}

/** Previews **/

@Preview(showBackground = true)
@Composable
fun TypeChipPreview() {
    TypeChip(
        PokemonType(
            name = "grass"
        )
    )
}
