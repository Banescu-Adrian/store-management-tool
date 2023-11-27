package com.store.services;

import com.store.dtos.AuthenticationDTO;
import com.store.entities.User;
import com.store.repositories.UserRepository;
import com.store.security.services.EncryptionService;
import com.store.security.services.JWTService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final com.store.security.services.JWTService JWTService;

    public AuthenticationService(UserRepository userRepository, EncryptionService encryptionService, JWTService JWTService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.JWTService = JWTService;
    }

    public String authenticate(AuthenticationDTO authenticationDTO) {
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(authenticationDTO.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (encryptionService.verifyPassword(authenticationDTO.getPassword(), user.getPassword())) {
                return JWTService.generateToken(user);
            }
        }

        return null;
    }
}
