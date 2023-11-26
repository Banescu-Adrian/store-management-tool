package com.store.store.user;

import com.store.store.security.services.EncryptionService;
import com.store.store.user.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    public void createUser(CreateUserDTO createUserDTO) throws Exception {
        if (userRepository.findByEmailIgnoreCase(createUserDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        try {
            User user = new User();

            user.setEmail(createUserDTO.getEmail());
            user.setPassword(encryptionService.encryptPassword(createUserDTO.getPassword()));
            user.setRole(createUserDTO.getRole());

            userRepository.save(user);
        } catch (Exception exception) {
            logger.error("Error on createUser", exception);

            throw new Exception();
        }
    }
}
