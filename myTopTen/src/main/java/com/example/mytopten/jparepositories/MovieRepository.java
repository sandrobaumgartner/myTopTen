package com.example.mytopten.jparepositories;

import com.example.mytopten.models.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :partOfTitle,'%'))")
    Optional<List<Movie>> getMoviesByPartOfTitle(String partOfTitle, Pageable pageable);
}
