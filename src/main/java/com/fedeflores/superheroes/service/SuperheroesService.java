package com.fedeflores.superheroes.service;

import com.fedeflores.superheroes.model.SuperheroDTO;

import java.util.List;

public interface SuperheroesService {

    List<SuperheroDTO> getAllSuperheroes();

    SuperheroDTO getSuperheroById(int id);

    List<SuperheroDTO> getSuperheroesByName(String name);

    SuperheroDTO updateSuperhero(int id, SuperheroDTO requestedChanges);

    void deleteSuperhero(int id);


}
