package com.pokemonreview.api.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonPageResponse;
import com.pokemonreview.api.exceptions.PokemonNotFoundException;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.PokemonService;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        // Pokemon pokemon = new Pokemon();
        // pokemon.setName(pokemonDto.getName());
        // pokemon.setType(pokemonDto.getType());
        Pokemon pokemon = mapToEntity(pokemonDto);

        Pokemon newPokemon = pokemonRepository.save(pokemon);

        // PokemonDto pokemonResponse = new PokemonDto();
        // pokemonResponse.setId(newPokemon.getId());
        // pokemonResponse.setName(newPokemon.getName());
        // pokemonResponse.setType(newPokemon.getType());
        PokemonDto pokemonResponse = mapToDto(newPokemon);

        return pokemonResponse;
    }

    @Override
    public PokemonPageResponse getAllPokemons(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);

        List<Pokemon> listOfPokemon = pokemons.getContent();

       List<PokemonDto> content = listOfPokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

       PokemonPageResponse pokemonPageResponse = new PokemonPageResponse();
       pokemonPageResponse.setContent(content);
       pokemonPageResponse.setPageNo(pokemons.getNumber());
       pokemonPageResponse.setPageSize(pokemons.getSize());
       pokemonPageResponse.setTotalElements(pokemons.getTotalElements());
       pokemonPageResponse.setTotalPages(pokemons.getTotalPages());
       pokemonPageResponse.setLast(pokemons.isLast());

       return pokemonPageResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));

        return mapToDto(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());

        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        return pokemon;
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon update failed."));

        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);

        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon delete failed."));
        pokemonRepository.delete(pokemon);
    }

    
}
