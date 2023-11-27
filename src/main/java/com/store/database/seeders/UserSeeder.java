package com.store.database.seeders;

import com.store.dtos.CreateUserDTO;
import com.store.services.UserService;
import com.store.enums.Role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserService userService;

    public UserSeeder(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "!@#Password",
                "admin@store.com",
                Role.ADMIN
        );

        userService.createUser(createUserDTO);
    }
}
