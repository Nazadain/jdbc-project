package ru.nikita.validator;

public interface Validator<T> {
    ValidationResult isValid(T obj);
}
