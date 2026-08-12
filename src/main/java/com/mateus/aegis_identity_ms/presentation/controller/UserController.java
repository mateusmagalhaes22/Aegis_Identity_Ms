package com.mateus.aegis_identity_ms.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mateus.aegis_identity_ms.application.useCase.UpdateUserUseCase;
import com.mateus.aegis_identity_ms.application.useCase.GetUserUseCase;
import com.mateus.aegis_identity_ms.application.useCase.DeleteUserUseCase;
import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.presentation.dto.UpdateUserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UpdateUserUseCase updateUserUseCase;

    private final GetUserUseCase getUserUseCase;

    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(UpdateUserUseCase updateUserUseCase, GetUserUseCase getUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.updateUserUseCase = updateUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(getUserUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return getUserUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody UpdateUserDTO user) {
        User existingUser = getUserUseCase.findById(id).orElse(null);
        if (existingUser != null) {
            User updatedUser = updateUserUseCase.update(user, id);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
