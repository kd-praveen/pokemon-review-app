package com.pokemonreview.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}
