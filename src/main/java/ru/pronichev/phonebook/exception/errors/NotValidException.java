package ru.pronichev.phonebook.exception.errors;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
