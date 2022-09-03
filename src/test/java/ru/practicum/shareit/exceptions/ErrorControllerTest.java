package ru.practicum.shareit.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.Conflict;
import ru.practicum.shareit.exceptions.errors.InternalServerError;
import ru.practicum.shareit.exceptions.errors.NotFound;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorControllerTest {

    private BadRequest badRequest;
    private Conflict conflict;
    private InternalServerError internalServerError;
    private NotFound notFound;
    private String badRequestMessage;
    private String conflictMessage;
    private String internalServerErrorMessage;
    private String notFoundMessage;
    private ErrorController errorController;

    @BeforeEach
    void setUp() {
        badRequestMessage = "Bad request";
        badRequest = new BadRequest(badRequestMessage);
        conflictMessage = "Conflict";
        conflict = new Conflict(conflictMessage);
        internalServerErrorMessage = "InternalServerError";
        internalServerError = new InternalServerError(internalServerErrorMessage);
        notFoundMessage = "Not found";
        notFound = new NotFound(notFoundMessage);
        errorController = new ErrorController();
    }

    @Test
    void handleIncorrectParameterException() {
        assertEquals(badRequestMessage, errorController.handleIncorrectParameterException(badRequest));
    }

    @Test
    void testHandleIncorrectParameterException() {
        assertEquals(notFoundMessage, errorController.handleIncorrectParameterException(notFound));
    }

    @Test
    void testHandleIncorrectParameterException1() {
        assertEquals(conflictMessage, errorController.handleIncorrectParameterException(conflict));
    }

    @Test
    void testHandleIncorrectParameterException2() {
        assertEquals(internalServerErrorMessage, errorController.handleIncorrectParameterException(internalServerError).get("error"));
    }
}