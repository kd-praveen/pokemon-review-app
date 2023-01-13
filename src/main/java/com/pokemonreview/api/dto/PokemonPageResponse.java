package com.pokemonreview.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class PokemonPageResponse {
    private List<PokemonDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
