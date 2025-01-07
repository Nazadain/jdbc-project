package ru.nikita.service;

import ru.nikita.dao.EmployeeDao;
import ru.nikita.dto.EmployeeDto;

import java.util.List;
import java.util.stream.Collectors;

public final class EmployeeService {
    private static EmployeeService instance;
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public List<EmployeeDto> findAll() {
        return employeeDao.findAll().stream()
                .map(employee ->
                        new EmployeeDto(
                                employee.getId(),
                                employee.getAge(),
                                employee.getFirstName() + " " + employee.getLastName(),
                                "Age: %s Company: %s".formatted(
                                        employee.getAge(),
                                        employee.getCompany().getName()
                                )
                        ))
                .toList();
    }

    public List<EmployeeDto> findByCompanyId(Long id) {
        return List.of();
    }

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        if (instance == null) {
            instance = new EmployeeService();
        }
        return instance;
    }
}
