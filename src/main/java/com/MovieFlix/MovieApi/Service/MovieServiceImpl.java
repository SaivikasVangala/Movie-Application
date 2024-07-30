package com.MovieFlix.MovieApi.Service;

import com.MovieFlix.MovieApi.DTO.MovieDTO;
import com.MovieFlix.MovieApi.Entities.Movie;
import com.MovieFlix.MovieApi.Exceptions.FileExistsException;
import com.MovieFlix.MovieApi.Exceptions.MovieNotFoundException;
import com.MovieFlix.MovieApi.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service("Movies")
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;
    //This will retrive the baseUrl value for us from the application.yml file.

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException {

        //1.Upload the file
        if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename()))){
           throw new FileExistsException("File already exists");
        }

          String uploadedFileName = fileService.uploadFile(path,file);

        //2. set value of the field 'poster' as filename
        //This is because filename which we are uploading and filename which we are saving in database should match

        movieDTO.setPoster(uploadedFileName);

        //3. Map moviedto to movie object.

        Movie movie = new Movie(movieDTO.getMovieId(), movieDTO.getTitle(),movieDTO.getDirector(),
                        movieDTO.getStudio(), movieDTO.getMovieCast(), movieDTO.getReleaseYear(),
                movieDTO.getPoster());

        //4. Save the movie object return saved object.

        Movie savedMovie = movieRepository .save(movie);

        //5. generate posterUrl

        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        //6. Map movie object to dto object and return it.

        MovieDTO response = new MovieDTO(savedMovie.getId(), savedMovie.getTitle(),
                savedMovie.getDirector(), savedMovie.getStudio(), savedMovie.getMovieCast(),savedMovie.getReleaseYear(),
                savedMovie.getPoster(), posterUrl);


        return response;
    }

    @Override
    public MovieDTO getMovieById(int movieId) {

      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id "+movieId));

      String posterUrl = baseUrl+"/file/"+movie.getPoster();

      MovieDTO reponse = new MovieDTO(movie.getId(),movie.getTitle(),
              movie.getDirector(), movie.getPoster(), movie.getMovieCast(),movie.getReleaseYear(),
              movie.getPoster(), posterUrl);

      return reponse;
    }

    @Override
    public List<MovieDTO> getAllMovies() {

        List<Movie> movies = movieRepository.findAll();

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for(Movie movie : movies){

            String posterUrl = baseUrl+"/file/"+movie.getPoster();

            MovieDTO movieDTO = new MovieDTO(movie.getId(),
                    movie.getTitle(), movie.getDirector(), movie.getStudio(), movie.getMovieCast(),
                    movie.getReleaseYear(), movie.getPoster(), posterUrl);

            movieDTOS.add(movieDTO);

        }


        return movieDTOS;
    }

    @Override
    public MovieDTO updateMovie(int id, MovieDTO movieDTO, MultipartFile file) throws IOException {
     //1. Check movie object if is existing with given movieId.
        Movie mv = movieRepository.findById(id)
                .orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id "+id));


        //2.If file is null do nothing, If it is not then delete the existing record and add new one.

        String fileName = mv.getPoster();

        if(file != null){
            Files.deleteIfExists(Paths.get(path+File.separator+ fileName));
            fileName = fileService.uploadFile(path,file);
        }

        //3. Set movieDto value according to step 2

        movieDTO.setPoster(fileName);

        //4.Map it to movie object

        Movie movie = new Movie(movieDTO.getMovieId(), movieDTO.getTitle(),movieDTO.getDirector(),
                movieDTO.getStudio(), movieDTO.getMovieCast(), movieDTO.getReleaseYear(),
                movieDTO.getPoster() );

        Movie saveMovie = movieRepository.save(movie);

        // 5 Generate posterUrl for it

        String posterUrl = baseUrl+"/file/"+ fileName;

        MovieDTO response = new MovieDTO(movie.getId(),movie.getTitle(),
                movie.getDirector(), movie.getPoster(), movie.getMovieCast(),movie.getReleaseYear(),
                movie.getPoster(), posterUrl);

        return response;
    }


    @Override
    public String deleteMovie(int id) throws IOException {

        Movie mv = movieRepository.findById(id)
                .orElseThrow(()-> new MovieNotFoundException("Movie Not Found with id "+id));

        int Id = mv.getId();

        String fileName = mv.getPoster();


            Files.deleteIfExists(Paths.get(path + File.separator+fileName));


        movieRepository.deleteById(id);
        return "Movie deleted with id: " + Id;
    }
}
