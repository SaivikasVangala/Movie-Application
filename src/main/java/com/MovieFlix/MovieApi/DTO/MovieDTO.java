package com.MovieFlix.MovieApi.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

//DTO class is responsible for interacting with controller and data related things.
    //This is resposible for mapping the actual values to the entities.
    private Integer movieId;


    @NotBlank(message = "Please Provide Movie Title")
    private String title;


    @NotBlank(message = "Please Provide Movie Director")
    private String director;

    @NotBlank(message = "Please Provide Movie Studio")
    private String studio;


    private Set<String> movieCast;


    private Integer releaseYear;


    @NotBlank(message = "Please Provide Movie poster")
    private String poster;

    @NotBlank(message = "Please Provide Movie posterUrl")
    private String posterUrl;
}
