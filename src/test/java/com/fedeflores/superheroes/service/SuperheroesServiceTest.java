package com.fedeflores.superheroes.service;

import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.repository.SuperheroesRepository;
import com.fedeflores.superheroes.repository.entity.Superhero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SuperheroesServiceTest {

    @Autowired
    private SuperheroesService superheroesService;

    @MockBean
    private SuperheroesRepository superheroesRepository;

    @Test
    public void getAllSuperheroes() {
        Superhero sh1 = new Superhero(1, "Batman");
        Superhero sh2 = new Superhero(2, "Spiderman");
        Superhero sh3 = new Superhero(3, "Ironman");
        SuperheroDTO dto1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO dto2 = new SuperheroDTO(2, "Spiderman");
        SuperheroDTO dto3 = new SuperheroDTO(3, "Ironman");
        List<SuperheroDTO> dtos = Arrays.asList(dto1, dto2, dto3);

        when(superheroesRepository.findAll()).thenReturn(Arrays.asList(sh1, sh2, sh3));

        List<SuperheroDTO> response = superheroesService.getAllSuperheroes();

        assertEquals(dtos, response);
    }

    @Test
    public void getSuperheroById() throws SuperheroNotFoundException {
        Superhero sh = new Superhero(1, "Batman");
        SuperheroDTO dto = new SuperheroDTO(1, "Batman");

        when(superheroesRepository.findById(anyInt())).thenReturn(Optional.of(sh));

        SuperheroDTO response = superheroesService.getSuperheroById(1);

        assertEquals(dto, response);
    }

    @Test
    public void getSuperheroById_NotFoundException() {
        when(superheroesRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(SuperheroNotFoundException.class, () -> superheroesService.getSuperheroById(1));
    }

    @Test
    public void getSuperheroesByName() {
        Superhero sh1 = new Superhero(1, "Batman");
        Superhero sh2 = new Superhero(2, "Spiderman");
        SuperheroDTO dto1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO dto2 = new SuperheroDTO(2, "Spiderman");
        List<SuperheroDTO> dtos = Arrays.asList(dto1, dto2);

        when(superheroesRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(sh1, sh2));

        List<SuperheroDTO> response = superheroesService.getSuperheroesByName("man");

        assertEquals(dtos, response);
    }

    @Test
    public void updateSuperhero() throws SuperheroNotFoundException {
        Superhero foundSuperhero = new Superhero(1, "Batman");
        SuperheroDTO updateRequested = new SuperheroDTO();
        updateRequested.setName("Captain America");
        Superhero updatedSuperhero = new Superhero(1, "Captain America");
        SuperheroDTO expectedResponse = new SuperheroDTO(1, "Captain America");

        when(superheroesRepository.findById(anyInt())).thenReturn(Optional.of(foundSuperhero));
        when(superheroesRepository.save(any(Superhero.class))).thenReturn(updatedSuperhero);

        SuperheroDTO response = superheroesService.updateSuperhero(1, updateRequested);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void updateSuperhero_NotFoundException() {
        SuperheroDTO updateRequested = new SuperheroDTO();
        updateRequested.setName("Captain America");

        when(superheroesRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(SuperheroNotFoundException.class, () -> superheroesService.updateSuperhero(1, updateRequested));
    }

    @Test
    public void deleteSuperhero() throws SuperheroNotFoundException {
        when(superheroesRepository.existsById(anyInt())).thenReturn(true);

        superheroesService.deleteSuperhero(1);

        verify(superheroesRepository, times(1)).deleteById(1);
    }

    @Test
    public void deleteSuperhero_NotFoundException() {
        when(superheroesRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(SuperheroNotFoundException.class, () -> superheroesService.deleteSuperhero(1));
    }
}