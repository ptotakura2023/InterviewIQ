package com.interviewiq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.interviewiq.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    /* Custom query method to find a user by email. This is for 
     * authentication and checking if a user already exists 
    */
    Optional<User> findByEmail(String email);
    
    // Check if an email is already taken
    boolean existsByEmail(String email);

}
