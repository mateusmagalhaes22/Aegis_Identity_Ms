package com.mateus.aegis_identity_ms.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.domain.repository.UserRepository;

@Repository
public class UserRepositoryImplementation implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImplementation(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id);
    }

    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    public void deleteById(UUID id) {
        jpaUserRepository.deleteById(id);
    }
}
