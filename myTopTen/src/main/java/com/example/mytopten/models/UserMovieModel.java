package com.example.mytopten.models;

public class UserMovieModel {

    private final long userId;
    private final long movieId;
    private final int position;

    public UserMovieModel(long userId, long movieId, int position) {
        this.userId = userId;
        this.movieId = movieId;
        this.position = position;
    }

    public long getUserId() {
        return userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public int getPosition() {
        return position;
    }
}
