package com.store.dtos;

import com.store.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotNull
    @NotBlank
    @Size(min = 10)
    @Size(max = 255)
    public String password;

    @Email
    @NotNull
    @NotBlank
    @Size(min = 3)
    @Size(max = 255)
    public String email;

    public Role role = Role.OPERATOR;
}
