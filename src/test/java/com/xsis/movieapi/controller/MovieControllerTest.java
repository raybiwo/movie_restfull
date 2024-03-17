package com.xsis.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsis.movieapi.model.dto.MovieDto;
import com.xsis.movieapi.model.request.MovieRequest;
import com.xsis.movieapi.model.request.MovieUpdateRequest;
import com.xsis.movieapi.model.response.APIResponse;
import com.xsis.movieapi.services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {
    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(movieController).build();
    }

    private MovieDto generateMovieDto(Integer id) {
        return MovieDto.builder()
                .id(id)
                .title("Pengabdi Setan 2 Comunion")
                .description("adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan " +
                        "ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi " +
                        "Setan.")
                .rating(7.0F)
                .image("RoI2PJqem+ZnxBTEUFygEw==")
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

    private MovieRequest generateMovieRequest(Integer id) {
        return MovieRequest.builder()
                .id(id)
                .title("Pengabdi Setan 2 Comunion")
                .description("adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan " +
                        "ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi " +
                        "Setan.")
                .rating(7.0F)
                .image("RoI2PJqem+ZnxBTEUFygEw==")
                .build();
    }

    private MovieUpdateRequest generateMovieUpdateRequest(Integer id) {
        return MovieUpdateRequest.builder()
                .id(id)
                .title("Pengabdi Setan 2 Comunion")
                .description("adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan " +
                        "ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi " +
                        "Setan.")
                .rating(7.0F)
                .image("RoI2PJqem+ZnxBTEUFygEw==")
                .build();
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void list_successFalse_emptyMovieList() throws Exception {
        APIResponse<List<MovieDto>> response = APIResponse.notFound("Movie not found");
        Mockito.when(movieService.listMovie()).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(get("/Movies")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void list_successTrue_createMovieList() throws Exception {
        APIResponse<List<MovieDto>> response = APIResponse.ok(List.of(generateMovieDto(1)), "Movie found");
        Mockito.when(movieService.listMovie()).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(get("/Movies")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void detail_successFalse_emptyMovieList() throws Exception {
        APIResponse<MovieDto> response = APIResponse.notFound("Movie not found");
        Mockito.when(movieService.detailMovie(1)).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(get("/Movies/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void detail_successTrue_createMovieList() throws Exception {
        APIResponse<MovieDto> response = APIResponse.ok(generateMovieDto(1), "Movie found");
        Mockito.when(movieService.detailMovie(1)).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(get("/Movies/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void add_successTrue_createMovieDto() throws Exception {
        APIResponse<MovieDto> response = APIResponse.ok(generateMovieDto(1), "Movie found");
        MovieRequest movieRequest = generateMovieRequest(1);
        Mockito.when(movieService.addMovie(ArgumentMatchers.any())).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/Movies")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(movieRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void update_successTrue_createMovieDto() throws Exception {
        APIResponse<MovieDto> response = APIResponse.ok(generateMovieDto(1), "Movie found");
        MovieUpdateRequest movieUpdateRequest = generateMovieUpdateRequest(1);
        Mockito.when(movieService.updateMovie(ArgumentMatchers.any())).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(patch("/Movies")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(movieUpdateRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void delete_successTrue_createMovieDto() throws Exception {
        APIResponse<MovieDto> response = APIResponse.ok(null, "Movie Deleted Successfully");
        Mockito.when(movieService.deleteMovie(1)).thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(delete("/Movies/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
