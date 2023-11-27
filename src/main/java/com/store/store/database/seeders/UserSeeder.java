package com.store.store.database.seeders;

import com.store.store.authorization.Role;
import com.store.store.user.CreateUserDTO;
import com.store.store.user.UserService;

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
