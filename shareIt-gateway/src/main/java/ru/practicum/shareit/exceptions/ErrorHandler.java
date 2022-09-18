package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.exceptions.errors.BadRequestException;
import ru.practicum.shareit.exceptions.errors.Conflict;
import ru.practicum.shareit.exceptions.errors.InternalServerError;
import ru.practicum.shareit.exceptions.errors.NotFound;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    /*
    @ExceptionHandler
    public ResponseEntity<?> handle(final BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(final NotFound exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
     */

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(NotFound exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleException(Conflict exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(InternalServerError exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }
}
