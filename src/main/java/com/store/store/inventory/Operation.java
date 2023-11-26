package com.store.store.inventory;

public enum Operation {
    ADD(1),
    SUBSTRACT(2);

    private final Integer operation;

    Operation(int operation) {
        this.operation = operation;
    }

    private Integer getOperation() {
        return operation;
    }
}
