package com.microservices.movieinfoservice.model;

public class MovieInfo {

        private String movieId;
        private String movieName;
        private String yearOfMaking;
        private String description;


    public MovieInfo(String movieId, String movieName, String yearOfMaking, String description) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.yearOfMaking = yearOfMaking;
        this.description = description;
    }

    public MovieInfo() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYearOfMaking() {
        return yearOfMaking;
    }

    public void setYearOfMaking(String yearOfMaking) {
        this.yearOfMaking = yearOfMaking;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
