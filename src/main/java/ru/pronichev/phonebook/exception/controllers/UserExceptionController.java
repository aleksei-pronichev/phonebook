package ru.pronichev.phonebook.exception.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.pronichev.phonebook.exception.errors.NotFoundException;
import ru.pronichev.phonebook.exception.errors.NotValidException;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NotValidException.class)
    public ResponseEntity<String> notValid(NotValidException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}