package com.anderson.dev.projectmanagement.application.port.out;

import com.anderson.dev.projectmanagement.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
