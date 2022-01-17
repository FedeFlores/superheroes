package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.exception.SuperheroNotFoundException;
import com.fedeflores.superheroes.model.SuperheroDTO;
import com.fedeflores.superheroes.service.SuperheroesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuperheroesController.class)
public class SuperheroesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroesService superheroesService;

    @Test
    void getAllSuperheroes() throws Exception {
        SuperheroDTO sh1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO sh2 = new SuperheroDTO(2, "Spiderman");
        SuperheroDTO sh3 = new SuperheroDTO(3, "Ironman");

        when(superheroesService.getAllSuperheroes()).thenReturn(Arrays.asList(sh1, sh2,sh3));

        mockMvc.perform(get("/superheroes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
        verify(superheroesService, times(1)).getAllSuperheroes();
    }

    @Test
    void getAllSuperheroes_ReturnsNoContent() throws Exception {
        when(superheroesService.getAllSuperheroes()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/superheroes/all"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
        verify(superheroesService, times(1)).getAllSuperheroes();
    }

    @Test
    void getSuperheroById() throws Exception {
        SuperheroDTO sh = new SuperheroDTO(1, "Batman");

        when(superheroesService.getSuperheroById(anyInt())).thenReturn(sh);

        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
        verify(superheroesService, times(1)).getSuperheroById(anyInt());
    }

    @Test
    void getSuperheroById_ReturnsNotFound() throws Exception {
        when(superheroesService.getSuperheroById(anyInt()))
                .thenThrow(new SuperheroNotFoundException("Superhero not found"));

        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Superhero not found")));
        verify(superheroesService, times(1)).getSuperheroById(anyInt());
    }

    @Test
    void getSuperheroesByName() throws Exception {
        SuperheroDTO sh1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO sh2 = new SuperheroDTO(2, "Spiderman");

        when(superheroesService.getSuperheroesByName(anyString())).thenReturn(Arrays.asList(sh1, sh2));

        mockMvc.perform(get("/superheroes").param("name", "man"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
        verify(superheroesService, times(1)).getSuperheroesByName(anyString());
    }

    @Test
    void getSuperheroesByName_ReturnsNoContent() throws Exception {
        when(superheroesService.getSuperheroesByName(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/superheroes").param("name", "man"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
        verify(superheroesService, times(1)).getSuperheroesByName(anyString());
    }

    @Test
    void updateSuperhero() throws Exception {
        SuperheroDTO sh = new SuperheroDTO(1, "Batman");

        when(superheroesService.updateSuperhero(anyInt(), any(SuperheroDTO.class))).thenReturn(sh);

        mockMvc.perform(put("/superhero/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"Batman\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
        verify(superheroesService, times(1)).updateSuperhero(anyInt(), any(SuperheroDTO.class));
    }

    @Test
    void updateSuperhero_ReturnsNotFound() throws Exception {
        when(superheroesService.updateSuperhero(anyInt(), any(SuperheroDTO.class)))
                .thenThrow(new SuperheroNotFoundException("Superhero not found"));

        mockMvc.perform(put("/superhero/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"Batman\" }"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Superhero not found")));
        verify(superheroesService, times(1)).updateSuperhero(anyInt(), any(SuperheroDTO.class));
    }

    @Test
    void deleteSuperhero() throws Exception {
        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNoContent());
        verify(superheroesService, times(1)).deleteSuperhero(anyInt());
    }

    @Test
    void deleteSuperhero_ReturnsNotFound() throws Exception {
        doThrow(new SuperheroNotFoundException("Superhero not found")).when(superheroesService).deleteSuperhero(anyInt());

        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Superhero not found")));
        verify(superheroesService, times(1)).deleteSuperhero(anyInt());
    }


}
