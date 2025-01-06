package ru.nikita.exception;

public class DaoException extends RuntimeException {
    public DaoException(Throwable e) {
        super(e);
    }
}
