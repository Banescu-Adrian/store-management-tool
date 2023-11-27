package com.store.store.inventory;

public enum Operation {
    ADD(1),
    SUBTRACT(2);

    private final Integer operation;

    Operation(int operation) {
        this.operation = operation;
    }

    private Integer getOperation() {
        return operation;
    }
}
