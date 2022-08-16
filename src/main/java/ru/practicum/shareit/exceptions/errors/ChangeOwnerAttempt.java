package ru.practicum.shareit.exceptions.errors;

public class ChangeOwnerAttempt extends RuntimeException {
    public ChangeOwnerAttempt(String msg) {
        super(msg);
    }
}
