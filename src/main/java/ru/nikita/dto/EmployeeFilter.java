package ru.nikita.dto;

public record EmployeeFilter(String firstName,
                             String lastName,
                             int companyId,
                             int limit,
                             int offset) {
}
