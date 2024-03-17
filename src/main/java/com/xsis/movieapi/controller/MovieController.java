package com.xsis.movieapi.controller;

import com.xsis.movieapi.model.dto.MovieDto;
import com.xsis.movieapi.model.request.MovieRequest;
import com.xsis.movieapi.model.request.MovieUpdateRequest;
import com.xsis.movieapi.model.response.APIResponse;
import com.xsis.movieapi.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Object> list() {
        APIResponse<List<MovieDto>> response = movieService.listMovie();
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatus())).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> detail(@PathVariable Integer id) {
        APIResponse<MovieDto> response = movieService.detailMovie(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatus())).body(response);
    }

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody MovieRequest movieRequest) {
        APIResponse<MovieDto> response = movieService.addMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatus())).body(response);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> update(@Validated @PathVariable Integer id, @RequestBody MovieUpdateRequest movieUpdateRequest) {
        movieUpdateRequest.setId(id);
        APIResponse<MovieDto> response = movieService.updateMovie(movieUpdateRequest);
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatus())).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        APIResponse<MovieDto> response = movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpStatus())).body(response);
    }
}
