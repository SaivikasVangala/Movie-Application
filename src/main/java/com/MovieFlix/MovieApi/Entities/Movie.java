package com.MovieFlix.MovieApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Movie")
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie Title")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie Director")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie Studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    private int releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please Provide Movie Poster")
    private String poster;
}
