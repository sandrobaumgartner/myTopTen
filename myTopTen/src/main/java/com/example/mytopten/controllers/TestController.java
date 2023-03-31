package com.example.mytopten.controllers;

import com.example.mytopten.jparepositories.MovieRepository;
import com.example.mytopten.jparepositories.UserMovieRepository;
import com.example.mytopten.jparepositories.UserRepository;
import com.example.mytopten.models.Movie;
import com.example.mytopten.models.User;
import com.example.mytopten.models.UserMovie;
import com.example.mytopten.models.UserMovieModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${myTopTen.app.cors}")
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserMovieRepository userMovieRepository;

    private final MovieRepository movieRepository;

    private final UserRepository userRepository;

    public TestController(UserMovieRepository userMovieRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/moviesByUser")
    public List<Movie> getMovieListByUser(@RequestParam Long id) {
        return userMovieRepository.findMoviesByUserId(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @GetMapping("/movie")
    public Movie getMovieById(@RequestParam Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @GetMapping("/moviesByPartOfTitle")
    public List<Movie> getMoviesByPartOfTitle(@RequestParam String partOfTitle) {
        return movieRepository.getMoviesByPartOfTitle(partOfTitle)
                .orElseThrow(() -> new RuntimeException("No movie found with containing string!"));
    }

    @PostMapping("/addMovieToList")
    @ResponseStatus(HttpStatus.OK)
    public void addUserMovie(@RequestBody UserMovieModel userMovieModel) {
        User user = userRepository.findById(userMovieModel.getUserId())
                .orElseThrow(() -> new RuntimeException("No user with id (" + userMovieModel.getUserId() + ") found!"));
        Movie movie = movieRepository.findById(userMovieModel.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie with id (" + userMovieModel.getMovieId() + ") found!"));
        UserMovie userMovie = new UserMovie(user, movie, userMovieModel.getPosition());
        userMovieRepository.save(userMovie);
    }
}
