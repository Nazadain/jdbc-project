package ru.nikita;

import ru.nikita.dao.CompanyDao;
import ru.nikita.dao.EmployeeDao;
import ru.nikita.entity.Company;

public class App {
    public static void main(String[] args) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        System.out.println(employeeDao.findAll());
    }
}
