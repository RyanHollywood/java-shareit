package ru.practicum.shareit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.errors.ChangeOwnerAttempt;
import ru.practicum.shareit.exceptions.errors.DuplicateEmailFound;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleIncorrectParameterException(final DuplicateEmailFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIncorrectParameterException(final ChangeOwnerAttempt exception) {
        return exception.getMessage();
    }
}