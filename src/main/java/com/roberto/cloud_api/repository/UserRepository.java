package com.roberto.cloud_api.repository;

import com.roberto.cloud_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * A simple database link specifically for user accounts.
 * Its main job is to help us look up a person by their username when they try to log in.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
