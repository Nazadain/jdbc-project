package ru.nikita.entity;

public enum Role {
    USER, ADMIN;

    public static String find(String role) {
        for (Role r : Role.values()) {
            if (r.toString().equals(role)) {
                return role;
            }
        }
        return "";
    }
}
