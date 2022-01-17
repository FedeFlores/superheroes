package com.fedeflores.superheroes.service;

import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;

import java.util.List;

public interface SuperheroesService {

    List<SuperheroDTO> getAllSuperheroes();

    SuperheroDTO getSuperheroById(int id) throws SuperheroNotFoundException;

    List<SuperheroDTO> getSuperheroesByName(String name);

    SuperheroDTO updateSuperhero(int id, SuperheroDTO requestedChanges) throws SuperheroNotFoundException;

    void deleteSuperhero(int id) throws SuperheroNotFoundException;


}
