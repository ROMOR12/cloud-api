package com.roberto.cloud_api.domain.port;

import com.roberto.cloud_api.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    User save(User user);
}
