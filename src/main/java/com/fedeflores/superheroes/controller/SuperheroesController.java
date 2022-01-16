package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.model.Superhero;
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
    public ResponseEntity<List<Superhero>> getAllSuperheroes(){
        return ResponseEntity.ok(superheroesService.getAllSuperheroes());
    }

    @GetMapping("/superhero/{id}")
    public ResponseEntity<Superhero> getSuperheroById(@PathVariable int id){
        return ResponseEntity.ok(superheroesService.getSuperheroById(id));
    }

    @GetMapping("/superheroes")
    public ResponseEntity<List<Superhero>> getSuperheroesByName(@RequestParam String name){
        return ResponseEntity.ok(superheroesService.getSuperheroesByName(name));
    }

    @PutMapping("/superhero/{id}")
    public ResponseEntity<Superhero> updateSuperhero(@PathVariable int id, @RequestBody Superhero superhero){
        return ResponseEntity.ok(superheroesService.updateSuperhero(id, superhero));
    }

    @DeleteMapping("/superhero/{id}")
    public ResponseEntity<HttpStatus> deleteSuperhero(@PathVariable int id){
        superheroesService.deleteSuperhero(id);
        return ResponseEntity.noContent().build();
    }

}
