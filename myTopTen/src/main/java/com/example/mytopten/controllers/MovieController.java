package com.example.mytopten.controllers;

import com.example.mytopten.jparepositories.MovieRepository;
import com.example.mytopten.jparepositories.UserMovieRepository;
import com.example.mytopten.jparepositories.UserRepository;
import com.example.mytopten.models.*;
import com.example.mytopten.security.services.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
                            userMovie.getMovie().getId(), userMovie.getMovie().getTitle(), userMovie.getPosition(),
                            userMovie.getMovie().getReleaseYear());
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity addMovieToList(@RequestBody MoviePositionModel moviePositionModel) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("No user with id (" + userDetails.getId() + ") found!"));
        Movie movie = movieRepository.findById(moviePositionModel.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie with id (" + moviePositionModel.getMovieId() + ") found!"));
        int position = moviePositionModel.getPosition();

        UserMovie userMovie = new UserMovie(user, movie, position);
        userMovieRepository.save(userMovie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePositions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity updatePositions(@RequestBody List<MoviePositionModel> moviePositionModels) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("No user with id (" + userDetails.getId() + ") found!"));

        for (MoviePositionModel moviePosition: moviePositionModels) {
            userMovieRepository.deleteMovieForUserById(moviePosition.getMovieId(), user.getId());
        }

        for (MoviePositionModel moviePosition: moviePositionModels) {
            Movie movie = movieRepository.findById(moviePosition.getMovieId())
                    .orElseThrow(() -> new RuntimeException("No movie with id (" + moviePosition.getMovieId() + ") found!"));
            UserMovie userMovie = new UserMovie(user, movie, moviePosition.getPosition());
            userMovieRepository.save(userMovie);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteMovieFromList(@RequestBody UserMovieId userMovieId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("No user with id (" + userDetails.getId() + ") found!"));
        userMovieId.setUserId(user.getId());

        List<UserMovie> userMovies = userMovieRepository.findMoviesByUserId(userMovieId.getUserId())
                .orElseThrow(() -> new RuntimeException("Error for userMovie with UserId (" + userMovieId.getUserId() + ")!"));;
        userMovieRepository.deleteMovieForUserById(userMovieId.getMovieId(), userMovieId.getUserId());

        int deletedPosition = 0;
        for (UserMovie userMovie : userMovies) {
            if(userMovie.getMovie().getId().equals(userMovieId.getMovieId())) {
                deletedPosition = userMovie.getPosition();
            }
            if(deletedPosition != 0) {
                if(userMovie.getPosition() > deletedPosition) {
                    userMovie.setPosition(userMovie.getPosition() - 1);
                    userMovieRepository.updatePositionForUserMovie(userMovie.getMovie().getId(),
                            userMovie.getUser().getId(), userMovie.getPosition());
                }
            }
        }
        return ResponseEntity.ok().build();
    }
}
