package com.example.mytopten.controllers;

import com.example.mytopten.jparepositories.MovieRepository;
import com.example.mytopten.jparepositories.UserMovieRepository;
import com.example.mytopten.jparepositories.UserRepository;
import com.example.mytopten.models.Movie;
import com.example.mytopten.models.MoviePositionModel;
import com.example.mytopten.models.User;
import com.example.mytopten.models.UserMovie;
import com.example.mytopten.security.services.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "${myTopTen.app.cors}")
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final UserMovieRepository userMovieRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public MovieController(UserMovieRepository userMovieRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Movie getMovieById(@RequestParam Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("GetMovieById error"));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MoviePositionModel> getMovieListByUser(@RequestParam Long userId) {
        List<UserMovie> userMovies = userMovieRepository.findMoviesByUserId(userId)
                .orElseThrow(() -> new RuntimeException("GetMovieListByUser error"));
        List<MoviePositionModel> movieList = new ArrayList<>();
        for (UserMovie userMovie : userMovies) {
            MoviePositionModel moviePositionModel =
                    new MoviePositionModel(
                            userMovie.getMovie().getId(), userMovie.getMovie().getTitle(),userMovie.getPosition());
            movieList.add(moviePositionModel);
        }
        return movieList;
    }

    @GetMapping("/byPartOfTitle")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Movie> getMoviesByPartOfTitle(@RequestParam String partOfTitle) {
        if(partOfTitle.equals("")) {
            return new ArrayList<>();
        }
        Pageable pageable = PageRequest.of(0, 8);
        return movieRepository.getMoviesByPartOfTitle(partOfTitle, pageable)
                .orElseThrow(() -> new RuntimeException("No movie found with containing string!"));
    }

    @PostMapping("/addToList")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void addMovieToList(@RequestBody MoviePositionModel moviePositionModel) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("No user with id (" + userDetails.getId() + ") found!"));
        Movie movie = movieRepository.findById(moviePositionModel.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie with id (" + moviePositionModel.getMovieId() + ") found!"));
        int position = moviePositionModel.getPosition();

        UserMovie userMovie = new UserMovie(user, movie, position);
        userMovieRepository.save(userMovie);
    }
}
