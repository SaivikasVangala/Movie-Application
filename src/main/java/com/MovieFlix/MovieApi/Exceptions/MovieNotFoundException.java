package com.MovieFlix.MovieApi.Exceptions;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String message){
        super(message);
    }
}
