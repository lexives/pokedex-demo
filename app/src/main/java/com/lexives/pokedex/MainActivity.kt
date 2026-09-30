package com.lexives.pokedex

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lexives.pokedex.ui.theme.PokedexDemoTheme
import com.lexives.pokedex.view.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        enableEdgeToEdge()

        setContent {
            PokedexDemoTheme {
                PokemonListScreen()
            }
        }
    }
}
