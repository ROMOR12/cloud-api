package com.roberto.cloud_api.infrastructure.repository;

import com.roberto.cloud_api.domain.model.User;
import com.roberto.cloud_api.domain.port.UserRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}