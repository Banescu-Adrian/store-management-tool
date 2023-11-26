package com.store.store.user;

import com.store.store.authorization.Role;
import com.store.store.inventory.Inventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Inventory> inventories = new LinkedHashSet<>();

}
