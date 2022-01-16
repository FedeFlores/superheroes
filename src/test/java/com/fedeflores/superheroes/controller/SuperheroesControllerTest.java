package com.fedeflores.superheroes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedeflores.superheroes.model.Superhero;
import com.fedeflores.superheroes.service.SuperheroesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuperheroesController.class)
public class SuperheroesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroesService superheroesService;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    void getAllSuperheroes() throws Exception {
        Superhero sh1 = new Superhero(1, "Batman");
        Superhero sh2 = new Superhero(2, "Spiderman");
        Superhero sh3 = new Superhero(3, "Ironman");

        when(superheroesService.getAllSuperheroes()).thenReturn(Arrays.asList(sh1, sh2,sh3));

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    void getSuperheroById() throws Exception {
        Superhero sh = new Superhero(1, "Batman");

        when(superheroesService.getSuperheroById(anyInt())).thenReturn(sh);

        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
    }

    @Test
    void getSuperheroesByName() throws Exception {
        Superhero sh1 = new Superhero(1, "Batman");
        Superhero sh2 = new Superhero(2, "Spiderman");

        when(superheroesService.getSuperheroesByName(anyString())).thenReturn(Arrays.asList(sh1, sh2));

        mockMvc.perform(get("/superheroes/{name}", "man"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void updateSuperhero() throws Exception {
        Superhero sh = new Superhero(1, "Batman");

        when(superheroesService.updateSuperhero(anyInt(), any(Superhero.class))).thenReturn(sh);

        mockMvc.perform(put("/superhero/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(sh)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
    }

    @Test
    void deleteSuperhero() throws Exception {
        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNoContent());
    }


}
