package com.xsis.movieapi.services;

import com.xsis.movieapi.model.dto.MovieDto;
import com.xsis.movieapi.model.entity.MovieEntity;
import com.xsis.movieapi.model.request.MovieRequest;
import com.xsis.movieapi.model.request.MovieUpdateRequest;
import com.xsis.movieapi.model.response.APIResponse;
import com.xsis.movieapi.repository.MovieRepository;
import com.xsis.movieapi.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.xsis.movieapi.util.Utils.getDateTimeNow;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Value("${api.response.movie.found}")
    private String movieFoundMsg;
    @Value("${api.response.movie.created.success}")
    private String movieCreatedMsg;
    @Value("${api.error.movie.not.found}")
    private String movieNotFoundMsg;
    @Value("${api.response.movie.updated.success}")
    private String movieUpdatedMsg;
    @Value("${api.response.movie.deleted.success}")
    private String movieDeletedMsg;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public APIResponse<List<MovieDto>> listMovie() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        if (movieEntities.isEmpty()) return APIResponse.notFound(movieNotFoundMsg);
        List<MovieDto> movieDtos = new ArrayList<>();
        movieEntities.forEach(x -> {
            MovieDto movieDto = new MovieDto();
            BeanUtils.copyProperties(x, movieDto);
            movieDtos.add(movieDto);
        });
        return APIResponse.ok(movieDtos, movieFoundMsg);
    }

    public APIResponse<MovieDto> addMovie(MovieRequest movieRequest) {
        MovieEntity movieEntity = MovieEntity.builder()
                .id(movieRequest.getId() != null ? movieRequest.getId() : null)
                .title(movieRequest.getTitle())
                .rating(movieRequest.getRating())
                .image(movieRequest.getImage())
                .description(movieRequest.getDescription())
                .createdAt(getDateTimeNow())
                .build();
        MovieEntity movieResult = movieRepository.save(movieEntity);
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movieResult, movieDto);
        return APIResponse.ok(movieDto, movieCreatedMsg);
    }

    public APIResponse<MovieDto> updateMovie(MovieUpdateRequest movieUpdateRequest) {

        MovieEntity movieEntity = movieRepository.findById(movieUpdateRequest.getId()).orElse(null);
        if (movieEntity == null) return APIResponse.notFound(movieNotFoundMsg);

        Utils.merge(movieUpdateRequest, movieEntity);
        movieEntity.setUpdatedAt(getDateTimeNow());
        MovieEntity result = movieRepository.save(movieEntity);
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(result, movieDto);
        return APIResponse.ok(movieDto, movieUpdatedMsg);
    }

    public APIResponse<MovieDto> deleteMovie(Integer id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElse(null);
        if (movieEntity == null) return APIResponse.notFound(movieNotFoundMsg);
        movieRepository.delete(movieEntity);
        return APIResponse.ok(null, movieDeletedMsg);
    }

    public APIResponse<MovieDto> detailMovie(Integer id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElse(null);
        if (movieEntity == null) return APIResponse.notFound(movieNotFoundMsg);
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movieEntity, movieDto);
        return APIResponse.ok(movieDto, movieFoundMsg);
    }
}
