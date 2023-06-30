package com.example.mytopten.jparepositories;

import com.example.mytopten.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :partOfUsername,'%')) " +
            "ORDER BY CASE WHEN LOWER(u.username) LIKE LOWER(:partOfUsername) THEN 1 " +
            "WHEN LOWER(u.username) LIKE LOWER(CONCAT(:partOfUsername,'%')) THEN 2 " +
            "WHEN LOWER(u.username) LIKE LOWER(CONCAT('%', :partOfUsername)) THEN 3 " +
            "ELSE 4 " +
            "END")
    Optional<List<User>> getUsersByPartOfUsername(String partOfUsername, Pageable pageable);
}
