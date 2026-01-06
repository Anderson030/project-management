package com.anderson.dev.projectmanagement.infrastructure.persistence.adapter;

import com.anderson.dev.projectmanagement.application.port.out.UserRepositoryPort;
import com.anderson.dev.projectmanagement.domain.model.User;
import com.anderson.dev.projectmanagement.infrastructure.persistence.entity.UserEntity;
import com.anderson.dev.projectmanagement.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository repository;

    public UserRepositoryAdapter(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setNew(true); // Force insert
        repository.save(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPassword()));
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
