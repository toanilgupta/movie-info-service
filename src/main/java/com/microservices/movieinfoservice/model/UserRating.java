package com.microservices.movieinfoservice.model;

import java.io.Serializable;

public class UserRating implements Serializable {


    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    private Long ratingId;
    private String userId;
    private String movieId;
    private String rating;

    public UserRating() {
    }

  public UserRating(Long ratingId, String userId, String movieId, String rating) {
      this.ratingId = ratingId;
      this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) {this.userId = userId;}

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating;}

}
