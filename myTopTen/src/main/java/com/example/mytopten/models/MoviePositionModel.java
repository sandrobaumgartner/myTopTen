package com.example.mytopten.models;

public class MoviePositionModel {

    private final long movieId;
    private final String title;
    private final int position;

    public MoviePositionModel(long movieId, String title, int position) {
        this.movieId = movieId;
        this.title = title;
        this.position = position;
    }

    public long getMovieId() {
        return movieId;
    }
    public String getTitle() {
        return title;
    }
    public int getPosition() {
        return position;
    }
}
