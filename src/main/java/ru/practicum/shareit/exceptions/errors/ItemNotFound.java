package ru.practicum.shareit.exceptions.errors;

public class ItemNotFound extends RuntimeException {
    public ItemNotFound(String msg) {
        super(msg);
    }
}
