package com.store.enums;

public enum Role {
    ADMIN(1),
    OPERATOR(2);

    private final Integer id;

    Role(int id) {
        this.id = id;
    }

    private Integer getId() {
        return id;
    }
}
