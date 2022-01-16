package com.fedeflores.superheroes.service.impl;

import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.repository.SuperheroesRepository;
import com.fedeflores.superheroes.repository.entity.Superhero;
import com.fedeflores.superheroes.service.SuperheroesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroesServiceImpl implements SuperheroesService {

    private SuperheroesRepository superheroesRepository;

    @Autowired
    public SuperheroesServiceImpl(SuperheroesRepository superheroesRepository) {
        this.superheroesRepository = superheroesRepository;
    }

    @Override
    public List<SuperheroDTO> getAllSuperheroes() {
        List<Superhero> superheroes = superheroesRepository.findAll();
        return superheroes.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    @Override
    public SuperheroDTO getSuperheroById(int id) {
        SuperheroDTO dto = new SuperheroDTO();
        // TODO si no existe, error
        Optional<Superhero> superheroOpt = superheroesRepository.findById(id);
        superheroOpt.ifPresent(sh -> BeanUtils.copyProperties(sh, dto));
        return dto;
    }

    @Override
    public List<SuperheroDTO> getSuperheroesByName(String name) {
        List<Superhero> superheroes = superheroesRepository.findByNameContainingIgnoreCase(name);
        return superheroes.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    @Override
    public SuperheroDTO updateSuperhero(int id, SuperheroDTO requestedChanges) {
        Optional<Superhero> superheroOpt = superheroesRepository.findById(id);
        //TODO si no existe, error
        if (superheroOpt.isEmpty()){

        }
        Superhero sh = superheroOpt.get();
        sh.setName(requestedChanges.getName());
        Superhero updatedSH = superheroesRepository.save(sh);
        SuperheroDTO updatedDTO = new SuperheroDTO();
        BeanUtils.copyProperties(updatedSH, updatedDTO);
        return updatedDTO;
    }

    @Override
    public void deleteSuperhero(int id) {
        //TODO si no existe, error
        if (superheroesRepository.existsById(id)) superheroesRepository.deleteById(id);

    }

    private SuperheroDTO copyToDTO(Superhero sh){
        SuperheroDTO dto = new SuperheroDTO();
        BeanUtils.copyProperties(sh, dto);
        return dto;
    }

}
