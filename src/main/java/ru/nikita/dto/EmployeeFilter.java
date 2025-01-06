package ru.nikita.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeFilter {
    private final String firstName;
    private final String lastName;
    private final int companyId;
    private final int limit;
    private final int offset;
}
