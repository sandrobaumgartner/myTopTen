package com.example.mytopten.controllers;

import com.example.mytopten.jparepositories.MovieRepository;
import com.example.mytopten.jparepositories.UserMovieRepository;
import com.example.mytopten.models.Movie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "${myTopTen.app.cors}")
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserMovieRepository userMovieRepository;

    private final MovieRepository movieRepository;

    public TestController(UserMovieRepository userMovieRepository, MovieRepository movieRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
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
        return movieRepository.getMoviesByPartOfTitle(partOfTitle).orElseThrow(() -> new RuntimeException("Error"));
    }
}
