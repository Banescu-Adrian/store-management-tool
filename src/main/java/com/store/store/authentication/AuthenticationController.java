package com.store.store.authentication;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String token = authenticationService.authenticate(authenticationDTO);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        AuthenticateResponse authenticateResponse = new AuthenticateResponse();

        authenticateResponse.setToken(token);

        return ResponseEntity.ok(authenticateResponse);
    }
}
