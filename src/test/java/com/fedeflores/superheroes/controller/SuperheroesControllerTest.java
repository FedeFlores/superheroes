package com.fedeflores.superheroes.controller;

import com.fedeflores.superheroes.service.SuperheroesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SuperheroesController.class)
public class SuperheroesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroesService superheroesService;

    @Test
    void getAllSuperheroes() {
    }

    @Test
    void getSuperheroById() {
    }

    @Test
    void getSuperheroByName() {
    }

    @Test
    void updateSuperhero() {
    }

    @Test
    void deleteSuperhero() {
    }


}
