package com.xsis.movieapi.services;

import com.xsis.movieapi.model.dto.MovieDto;
import com.xsis.movieapi.model.entity.MovieEntity;
import com.xsis.movieapi.model.request.MovieRequest;
import com.xsis.movieapi.model.request.MovieUpdateRequest;
import com.xsis.movieapi.model.response.APIResponse;
import com.xsis.movieapi.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MovieServicesTest {
    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    private List<MovieEntity> movieEntities;
    private String movieNotFoundMsg;

    @BeforeEach
    void beforeTest() {
        movieEntities = new ArrayList<>();
        movieEntities.add(generateMovieEntity(1));
        movieEntities.add(generateMovieEntity(2));

        movieNotFoundMsg = "Movie not found";
        ReflectionTestUtils.setField(movieService, "movieNotFoundMsg", movieNotFoundMsg);
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

    private MovieEntity generateMovieEntity(Integer id) {
        return MovieEntity.builder()
                .id(id)
                .title("Pengabdi Setan 2 Comunion")
                .description("adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan " +
                        "ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi " +
                        "Setan.")
                .rating(7.0F)
                .image("RoI2PJqem+ZnxBTEUFygEw==")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void listMovie_successFalse_movieNotFound() {
        Mockito.when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        APIResponse<List<MovieDto>> response = movieService.listMovie();
        Assertions.assertFalse(response.isSuccess());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void listMovie_successTrue_dataMovieExist() {
        Mockito.when(movieRepository.findAll()).thenReturn(movieEntities);
        APIResponse<List<MovieDto>> response = movieService.listMovie();
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getData().isEmpty());
        Assertions.assertEquals(2, response.getData().size());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void addMovie_successTrue_rightMovieRequest() {
        MovieEntity movieEntity = generateMovieEntity(1);
        Mockito.when(movieRepository.save(ArgumentMatchers.any())).thenReturn(movieEntity);
        APIResponse<MovieDto> response = movieService.addMovie(generateMovieRequest(1));
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(1, response.getData().getId());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void updateMovie_successFalse_wrongMovieId() {
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        APIResponse<MovieDto> response = movieService.updateMovie(generateMovieUpdateRequest(1));
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(movieNotFoundMsg, response.getMessage());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void updateMovie_successTrue_rightMovieId() {
        MovieEntity movieEntity = generateMovieEntity(1);
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(movieEntity));
        Mockito.when(movieRepository.save(ArgumentMatchers.any())).thenReturn(movieEntity);
        APIResponse<MovieDto> response = movieService.updateMovie(generateMovieUpdateRequest(1));
        Assertions.assertTrue(response.isSuccess());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void deleteMovie_successFalse_wrongMovieId() {
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        APIResponse<MovieDto> response = movieService.deleteMovie(1);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(movieNotFoundMsg, response.getMessage());
    }

    //MethodName_ExpectedBehavior_StateUnderTest
    @Test
    void deleteMovie_successTrue_rightMovieId() {
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(generateMovieEntity(1)));
        APIResponse<MovieDto> response = movieService.deleteMovie(1);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void detailMovie_successFalse_wrongMovieId() {
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        APIResponse<MovieDto> response = movieService.detailMovie(1);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(movieNotFoundMsg, response.getMessage());
    }

    @Test
    void detailMovie_successTrue_rightMovieId() {
        Mockito.when(movieRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(generateMovieEntity(1)));
        APIResponse<MovieDto> response = movieService.detailMovie(1);
        Assertions.assertTrue(response.isSuccess());
    }
}
