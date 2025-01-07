package ru.nikita.service;

import ru.nikita.dao.EmployeeDao;
import ru.nikita.dto.EmployeeDto;
import ru.nikita.entity.Employee;

import java.util.List;

public final class EmployeeService {
    private static EmployeeService instance;
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public List<EmployeeDto> findAll() {
        return buildEmployeeDtoList(employeeDao.findAll());
    }

    public List<EmployeeDto> findAllByCompanyId(Long id) {
        return buildEmployeeDtoList(employeeDao.findAllByCompanyId(id));
    }

    private List<EmployeeDto> buildEmployeeDtoList(List<Employee> employees) {
        return employees.stream()
                .map(employee ->
                        new EmployeeDto(
                                employee.getId(),
                                employee.getAge(),
                                "%s %s".formatted(
                                        employee.getFirstName(),
                                        employee.getLastName()),
                                "Age: %s Company: %s".formatted(
                                        employee.getAge(),
                                        employee.getCompany().getName()
                                )
                        ))
                .toList();
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
