package com.example.mytopten.jparepositories;

import com.example.mytopten.models.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {

    @Query("SELECT um FROM UserMovie um INNER JOIN Movie m ON um.movie.id = m.id WHERE um.user.id = :userId ORDER BY um.position")
    Optional<List<UserMovie>> findMoviesByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM UserMovie um WHERE um.movie.id = :movieId AND um.user.id = :userId")
    void deleteMovieForUserById(Long movieId, Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE FROM UserMovie um SET um.position = :newPosition WHERE um.movie.id = :movieId AND um.user.id = :userId")
    void updatePositionForUserMovie(Long movieId, Long userId, int newPosition);
    @SuppressWarnings("unchecked")
    UserMovie save(UserMovie userMovie);
}
