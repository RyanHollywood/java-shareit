package ru.practicum.shareit.exceptions.errors;

public class NoUserFound extends RuntimeException {
    public NoUserFound(String msg) {
        super(msg);
    }
}
