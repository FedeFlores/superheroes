package com.fedeflores.superheroes.integration;

import com.fedeflores.superheroes.repository.SuperheroesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SuperheroesIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SuperheroesRepository superheroesRepository;

    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllSuperheroes() throws Exception {
        mockMvc.perform(get("/superheroes/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(5)));
    }

    @Test
    public void getAllSuperheroes_ReturnsNoContent() throws Exception {
        mockMvc.perform(get("/superheroes/all"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getSuperheroById() throws Exception {
        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Batman")));
    }

    @Test
    public void getSuperheroById_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/superhero/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("No superhero found with id: 1")));
    }

    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getSuperheroesByName() throws Exception {
        mockMvc.perform(get("/superheroes").param("name", "man"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void getSuperheroesByName_ReturnsNoContent() throws Exception {
        mockMvc.perform(get("/superheroes").param("name", "man"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateSuperhero() throws Exception {
        mockMvc.perform(put("/superhero/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"Peter Parker\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Peter Parker")));
    }

    @Test
    public void updateSuperhero_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/superhero/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"Batman\" }"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("No superhero found with id: 1")));
    }

    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteSuperhero() throws Exception {
        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteSuperhero_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/superhero/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("No superhero found with id: 1")));
    }

}
