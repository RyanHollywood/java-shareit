package ru.practicum.shareit.exceptions.errors;

public class Conflict extends RuntimeException {
    public Conflict(String msg) {
        super(msg);
    }
}