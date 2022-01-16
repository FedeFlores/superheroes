package com.fedeflores.superheroes.service;

import com.fedeflores.superheroes.model.Superhero;

import java.util.List;

public interface SuperheroesService {

    List<Superhero> getAllSuperheroes();

    Superhero getSuperheroById(int id);

    List<Superhero> getSuperheroesByName(String name);

    Superhero updateSuperhero(int id, Superhero superhero);

    void deleteSuperhero(int id);


}
