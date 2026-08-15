package com.mateus.aegis_identity_ms.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mateus.aegis_identity_ms.domain.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(UUID id);

    void deleteById(UUID id);
}