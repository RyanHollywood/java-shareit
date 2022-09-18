package ru.practicum.shareit.exceptions.errors;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String msg) {
        super(msg);
    }
}
