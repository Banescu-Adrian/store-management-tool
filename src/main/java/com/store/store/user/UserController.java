package com.store.store.user;

import com.store.store.user.exceptions.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.createUser(createUserDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
