package com.MovieFlix.MovieApi.Repositories;

import com.MovieFlix.MovieApi.DTO.MovieDTO;
import com.MovieFlix.MovieApi.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
