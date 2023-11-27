package com.store.store.repositories;

import com.store.store.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
