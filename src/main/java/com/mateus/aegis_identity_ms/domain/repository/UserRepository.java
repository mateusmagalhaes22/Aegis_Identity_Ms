package com.mateus.aegis_identity_ms.domain.repository;

import java.util.Optional;

import com.mateus.aegis_identity_ms.domain.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}