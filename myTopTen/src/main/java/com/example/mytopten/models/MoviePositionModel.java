package com.example.mytopten.models;

public class MoviePositionModel {

    private final long movieId;
    private final String title;
    private final int position;

    private final int releaseYear;

    public MoviePositionModel(long movieId, String title, int position, int releaseYear) {
        this.movieId = movieId;
        this.title = title;
        this.position = position;
        this.releaseYear = releaseYear;
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
    public int getReleaseYear() {
        return releaseYear;
    }
}
