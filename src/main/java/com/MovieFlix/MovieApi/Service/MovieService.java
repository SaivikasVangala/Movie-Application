package com.MovieFlix.MovieApi.Service;

import com.MovieFlix.MovieApi.DTO.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException;

    MovieDTO getMovieById(int id);

    List<MovieDTO> getAllMovies();

    MovieDTO updateMovie(int id,MovieDTO movieDTO, MultipartFile file) throws IOException;

    String deleteMovie(int id) throws IOException;
}
