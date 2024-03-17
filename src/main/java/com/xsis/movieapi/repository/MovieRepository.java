package com.xsis.movieapi.repository;

import com.xsis.movieapi.model.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
