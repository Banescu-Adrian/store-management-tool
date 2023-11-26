package com.store.store.user;

import com.store.store.authorization.Role;
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
    @Min(10)
    @Max(255)
    public String password;

    @Email
    @NotNull
    @NotBlank
    @Min(3)
    @Max(255)
    public String email;

    @NotNull
    @NotBlank
    @Pattern(regexp="^(ADMIN|OPERATOR)$",message="Invalid role")
    public Role role;
}
