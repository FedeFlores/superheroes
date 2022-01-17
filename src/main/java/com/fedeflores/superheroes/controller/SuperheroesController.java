package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.service.SuperheroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SuperheroesController {

    private SuperheroesService superheroesService;

    @Autowired
    public SuperheroesController(SuperheroesService superheroesService) {
        this.superheroesService = superheroesService;
    }

    @GetMapping("/superheroes/all")
    public ResponseEntity<List<SuperheroDTO>> getAllSuperheroes(){
        List<SuperheroDTO> superheroes = superheroesService.getAllSuperheroes();
        return superheroes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(superheroes);
    }

    @GetMapping("/superhero/{id}")
    public ResponseEntity<SuperheroDTO> getSuperheroById(@PathVariable int id) throws SuperheroNotFoundException {
        return ResponseEntity.ok(superheroesService.getSuperheroById(id));
    }

    @GetMapping("/superheroes")
    public ResponseEntity<List<SuperheroDTO>> getSuperheroesByName(@RequestParam String name){
        List<SuperheroDTO> superheroes = superheroesService.getSuperheroesByName(name);
        return superheroes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(superheroes);
    }

    @PutMapping("/superhero/{id}")
    public ResponseEntity<SuperheroDTO> updateSuperhero(@PathVariable int id, @RequestBody SuperheroDTO superheroDTO) throws SuperheroNotFoundException {
        return ResponseEntity.ok(superheroesService.updateSuperhero(id, superheroDTO));
    }

    @DeleteMapping("/superhero/{id}")
    public ResponseEntity<HttpStatus> deleteSuperhero(@PathVariable int id) throws SuperheroNotFoundException {
        superheroesService.deleteSuperhero(id);
        return ResponseEntity.noContent().build();
    }

}
