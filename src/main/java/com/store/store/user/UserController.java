package com.store.store.user;

import com.store.store.authorization.Role;
import com.store.store.user.exceptions.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@SecurityScheme(
        name = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Users")
@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity createUser(
            @Valid @RequestBody CreateUserDTO createUserDTO,
            @AuthenticationPrincipal User user
    ) {
        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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
