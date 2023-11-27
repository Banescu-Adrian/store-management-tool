package com.store.store.database.seeders;

import com.store.store.enums.Role;
import com.store.store.dtos.CreateUserDTO;
import com.store.store.services.UserService;

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
