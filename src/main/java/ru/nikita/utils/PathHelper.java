package ru.nikita.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PathHelper {

    public static String create(String fileName) {
        return "/WEB-INF/" + fileName;
    }

}
