package com.mateus.aegis_identity_ms.useCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mateus.aegis_identity_ms.entity.AccountStatus;
import com.mateus.aegis_identity_ms.entity.CreateUserDTO;
import com.mateus.aegis_identity_ms.entity.CreateUserResponseDto;
import com.mateus.aegis_identity_ms.entity.UpdateUserDTO;
import com.mateus.aegis_identity_ms.entity.User;
import com.mateus.aegis_identity_ms.infrastructure.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public CreateUserResponseDto create(CreateUserDTO userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new RuntimeException("Email already registered.");
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(AccountStatus.ACTIVE);
        user.setCreatedAt(now);
        userRepository.save(user);

        return new CreateUserResponseDto(user.getName(), user.getEmail());
    }

    public User save(CreateUserDTO userDto) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setCreatedAt(now);
        return userRepository.save(user);
    }

    public User update(UpdateUserDTO userDto, UUID id) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(id);
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setBalance(userDto.balance());
        user.setUpdatedAt(now);
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
