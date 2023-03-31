package com.example.mytopten.models;

import javax.persistence.*;

@Entity
@Table(name = "user_movie",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"user_id", "position"})})
public class UserMovie {

    @EmbeddedId
    private final UserMovieId id = new UserMovieId();

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    private int position;

    public UserMovie() {
    }

    public UserMovie(User user, Movie movie, int position) {
        this.user = user;
        this.movie = movie;
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
