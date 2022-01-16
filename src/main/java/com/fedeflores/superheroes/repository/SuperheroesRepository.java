package com.fedeflores.superheroes.repository;

import com.fedeflores.superheroes.repository.entity.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperheroesRepository extends JpaRepository<Superhero, Integer> {

    List<Superhero> findByNameContainingIgnoreCase(String name);

}
