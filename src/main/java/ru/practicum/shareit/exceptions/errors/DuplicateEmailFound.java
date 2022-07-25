package ru.practicum.shareit.exceptions.errors;

public class DuplicateEmailFound extends RuntimeException {
    public DuplicateEmailFound(String msg) {
        super(msg);
    }
}
