package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.aspect.TimeLogger;
import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.service.SuperheroesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "List all superheroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroes found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuperheroDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "Superheroes don't exist",
                    content = @Content) })
    @TimeLogger
    @GetMapping("/superheroes/all")
    public ResponseEntity<List<SuperheroDTO>> getAllSuperheroes(){
        List<SuperheroDTO> superheroes = superheroesService.getAllSuperheroes();
        return superheroes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(superheroes);
    }

    @Operation(summary = "Get superhero by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuperheroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Superhero not found",
                    content = @Content) })
    @TimeLogger
    @GetMapping("/superhero/{id}")
    public ResponseEntity<SuperheroDTO> getSuperheroById(@PathVariable int id) throws SuperheroNotFoundException {
        return ResponseEntity.ok(superheroesService.getSuperheroById(id));
    }

    @Operation(summary = "Search superheroes by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superheroes found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuperheroDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No results",
                    content = @Content) })
    @TimeLogger
    @GetMapping("/superheroes")
    public ResponseEntity<List<SuperheroDTO>> getSuperheroesByName(@RequestParam String name){
        List<SuperheroDTO> superheroes = superheroesService.getSuperheroesByName(name);
        return superheroes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(superheroes);
    }

    @Operation(summary = "Update superhero by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuperheroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Superhero not found",
                    content = @Content) })
    @TimeLogger
    @PutMapping("/superhero/{id}")
    public ResponseEntity<SuperheroDTO> updateSuperhero(@PathVariable int id, @RequestBody SuperheroDTO superheroDTO) throws SuperheroNotFoundException {
        return ResponseEntity.ok(superheroesService.updateSuperhero(id, superheroDTO));
    }

    @Operation(summary = "Delete superhero by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuperheroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Superhero not found",
                    content = @Content) })
    @TimeLogger
    @DeleteMapping("/superhero/{id}")
    public ResponseEntity<HttpStatus> deleteSuperhero(@PathVariable int id) throws SuperheroNotFoundException {
        superheroesService.deleteSuperhero(id);
        return ResponseEntity.noContent().build();
    }

}
