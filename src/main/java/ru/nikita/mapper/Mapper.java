package ru.nikita.mapper;

public interface Mapper<T, F> {
    T mapFrom(F f);
}
