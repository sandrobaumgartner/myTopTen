package com.example.mytopten.models;

public class MoviePositionModel {

    private final long movieId;
    private final int position;

    public MoviePositionModel(long movieId, int position) {
        this.movieId = movieId;
        this.position = position;
    }

    public long getMovieId() {
        return movieId;
    }

    public int getPosition() {
        return position;
    }
}
