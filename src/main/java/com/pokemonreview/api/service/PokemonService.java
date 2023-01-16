package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonPageResponse;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);

    PokemonPageResponse getAllPokemons(int pageNo, int pageSize);

    PokemonDto getPokemonById(int id);

    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

    void deletePokemon(int id);
}
