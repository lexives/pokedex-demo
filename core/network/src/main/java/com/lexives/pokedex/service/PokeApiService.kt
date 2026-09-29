package com.lexives.pokedex.service

import com.lexives.pokedex.dto.PokemonListResponse
import com.lexives.pokedex.dto.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<PokemonListResponse>

    @GET("/pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: String
    ) : Response<PokemonResponse>
}
