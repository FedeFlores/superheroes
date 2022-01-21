package com.fedeflores.superheroes.service.impl;

import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.repository.SuperheroesRepository;
import com.fedeflores.superheroes.repository.entity.Superhero;
import com.fedeflores.superheroes.service.SuperheroesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroesServiceImpl implements SuperheroesService {

    private SuperheroesRepository superheroesRepository;

    private final static String NOTFOUND_MSG = "No superhero found with id: ";

    @Autowired
    public SuperheroesServiceImpl(SuperheroesRepository superheroesRepository) {
        this.superheroesRepository = superheroesRepository;
    }

    @Override
    @Cacheable("superheroes")
    public List<SuperheroDTO> getAllSuperheroes() {
        List<Superhero> superheroes = superheroesRepository.findAll();
        return superheroes.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable("superheroesById")
    public SuperheroDTO getSuperheroById(int id) throws SuperheroNotFoundException {
        SuperheroDTO dto = new SuperheroDTO();
        Superhero sh = superheroesRepository.findById(id).orElseThrow(() -> new SuperheroNotFoundException(NOTFOUND_MSG+ id));
        return copyToDTO(sh);
    }

    @Override
    @Cacheable("superheroesByName")
    public List<SuperheroDTO> getSuperheroesByName(String name) {
        List<Superhero> superheroes = superheroesRepository.findByNameContainingIgnoreCase(name.strip());
        return superheroes.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = { "superheroes", "superheroesById", "superheroesByName" }, allEntries = true)
    public SuperheroDTO updateSuperhero(int id, SuperheroDTO requestedChanges) throws SuperheroNotFoundException {
        Optional<Superhero> superheroOpt = superheroesRepository.findById(id);
        Superhero sh = superheroOpt.orElseThrow(() -> new SuperheroNotFoundException(NOTFOUND_MSG + id));
        sh.setName(requestedChanges.getName());
        Superhero updatedSH = superheroesRepository.save(sh);
        SuperheroDTO updatedDTO = new SuperheroDTO();
        BeanUtils.copyProperties(updatedSH, updatedDTO);
        return updatedDTO;
    }

    @Override
    @CacheEvict(value = { "superheroes", "superheroesById", "superheroesByName" }, allEntries = true)
    public void deleteSuperhero(int id) throws SuperheroNotFoundException {
        if (superheroesRepository.existsById(id)) {
            superheroesRepository.deleteById(id);
        } else {
            throw new SuperheroNotFoundException(NOTFOUND_MSG + id);
        }
    }

    private SuperheroDTO copyToDTO(Superhero sh){
        SuperheroDTO dto = new SuperheroDTO();
        BeanUtils.copyProperties(sh, dto);
        return dto;
    }

}
