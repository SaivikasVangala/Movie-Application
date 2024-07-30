package com.MovieFlix.MovieApi.Controller;


import com.MovieFlix.MovieApi.DTO.MovieDTO;
import com.MovieFlix.MovieApi.Exceptions.EmptyFileException;
import com.MovieFlix.MovieApi.Service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(@Qualifier("Movies") MovieService movieService) {
        this.movieService = movieService;

    }


    // When we are talking request in which file and json body data involed we use request part.
   @PostMapping("/AddMovie")
    public ResponseEntity<MovieDTO> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException, EmptyFileException {
        //In RequestPart if we pass an object it is not possible.In Json Body
       //It contains only text and file. so we are passing string.

       if(file.isEmpty()){
           throw new EmptyFileException("File not available. Please send a file");
           //This is our own customised exception. We customise according to our own need.
       }

       MovieDTO dto = convertToMovieDTO(movieDto);

    return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
   }

   //we need to convert the string into the json then only we can interact with service layer. so we create private  method which will do conversion.

   private MovieDTO convertToMovieDTO(String movieDtoObj) throws JsonProcessingException {


        //Spring web provides a json dependency an object mapper by using it we do.

       ObjectMapper mapper = new ObjectMapper();

       return mapper.readValue( movieDtoObj, MovieDTO.class);
   }

   @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovieById(movieId));
   }

   @GetMapping("/all")
   public ResponseEntity<List<MovieDTO>> getAllMovieHandler(){
        return ResponseEntity.ok(movieService.getAllMovies());
   }

   @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDTO> updateMovieHandler(@PathVariable int movieId, @RequestPart String movieDtoObj,
                                                       @RequestPart MultipartFile file) throws IOException {
        if(file.isEmpty()){
            file = null;
        }
        MovieDTO movieDTO = convertToMovieDTO(movieDtoObj);

        return  ResponseEntity.ok(movieService.updateMovie(movieId,movieDTO,file));
   }

   @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
   }
}
