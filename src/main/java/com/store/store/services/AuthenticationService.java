package com.store.store.services;

import com.store.store.dtos.AuthenticationDTO;
import com.store.store.security.services.EncryptionService;
import com.store.store.security.services.JWTService;
import com.store.store.entities.User;
import com.store.store.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final JWTService JWTService;

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
