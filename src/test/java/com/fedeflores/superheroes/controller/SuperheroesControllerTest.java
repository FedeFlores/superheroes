package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.model.SuperheroDTO;
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

    @Test
    void getAllSuperheroes() throws Exception {
        SuperheroDTO sh1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO sh2 = new SuperheroDTO(2, "Spiderman");
        SuperheroDTO sh3 = new SuperheroDTO(3, "Ironman");

        when(superheroesService.getAllSuperheroes()).thenReturn(Arrays.asList(sh1, sh2,sh3));

        mockMvc.perform(get("/superheroes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    void getSuperheroById() throws Exception {
        SuperheroDTO sh = new SuperheroDTO(1, "Batman");

        when(superheroesService.getSuperheroById(anyInt())).thenReturn(sh);

        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
    }

    @Test
    void getSuperheroesByName() throws Exception {
        SuperheroDTO sh1 = new SuperheroDTO(1, "Batman");
        SuperheroDTO sh2 = new SuperheroDTO(2, "Spiderman");

        when(superheroesService.getSuperheroesByName(anyString())).thenReturn(Arrays.asList(sh1, sh2));

        mockMvc.perform(get("/superheroes").param("name", "man"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
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
    }

    @Test
    void deleteSuperhero() throws Exception {
        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNoContent());
    }


}
