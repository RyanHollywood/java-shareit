package ru.practicum.shareit.exceptions.errors;

public class BadRequest extends RuntimeException {
    public BadRequest(String msg) {
        super(msg);
    }
}
