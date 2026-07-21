package com.mateus.aegis_identity_ms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mateus.aegis_identity_ms.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}