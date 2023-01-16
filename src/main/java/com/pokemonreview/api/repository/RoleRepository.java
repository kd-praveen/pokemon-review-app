package com.pokemonreview.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
    
}
