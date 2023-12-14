package ru.yandex.practicum.catsgram.exceptions;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
