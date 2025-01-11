package ru.nikita.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.nikita.dto.EmployeeFilter;
import ru.nikita.entity.Employee;
import ru.nikita.exception.DaoException;
import ru.nikita.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeDao implements Dao<Long, Employee> {
    private static final CompanyDao companyDao = CompanyDao.getInstance();
    private static EmployeeDao instance;

    private static final String SAVE =
            "INSERT INTO employee (first_name, last_name, age, company_id) VALUES (?,?,?,?)";

    private static final String FIND_ALL = "SELECT * FROM employee";

    private static final String FIND_ALL_BY_COMPANY = FIND_ALL + " WHERE company_id = ?";

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String UPDATE =
            "UPDATE employee SET first_name = ?, last_name = ?, age = ?, company_id = ? WHERE id = ?";

    private static final String DELETE_BY_ID = "DELETE FROM employee WHERE id = ?";

    @SneakyThrows
    public Long save(Employee employee) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getAge());
            statement.setLong(4, employee.getCompany().getId());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                employee.setId(keys.getLong(1));
            }
            return employee.getId();
        }
    }

    @Override
    @SneakyThrows
    public List<Employee> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = statement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                employees.add(buildEmployee(result));
            }
            return employees;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Employee> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Employee employee = null;
            if (result.next()) {
                employee = buildEmployee(result);
            }
            return Optional.ofNullable(employee);
        }
    }

    @Override
    @SneakyThrows
    public boolean update(Employee employee) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getAge());
            statement.setLong(4, employee.getCompany().getId());
            statement.setLong(5, employee.getId());
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    @SneakyThrows
    public List<Employee> findAllByCompanyId(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_COMPANY)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                employees.add(buildEmployee(result));
            }
            return employees;
        }
    }

    @SneakyThrows
    public List<Employee> findAll(EmployeeFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.firstName() != null) {
            parameters.add(filter.firstName());
            whereSql.add("first_name = ?");
        }
        if (filter.lastName() != null) {
            parameters.add(filter.lastName());
            whereSql.add("last_name = ?");
        }
        if (filter.companyId() != 0) {
            parameters.add(filter.companyId());
            whereSql.add("company_id = ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(Collectors.joining(
                        " AND ",
                        parameters.size() > 2 ? " WHERE " : "",
                        " LIMIT ? OFFSET ? "
                ));
        String sql = FIND_ALL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Employee> employees = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                employees.add(buildEmployee(result));
            }
            return employees;
        }
    }

    private static Employee buildEmployee(ResultSet result) throws SQLException {
        return new Employee(
                result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getInt("age"),
                companyDao.findById(result.getLong("company_id"),
                                result.getStatement().getConnection())
                        .orElse(null)
        );
    }

    public static EmployeeDao getInstance() {
        if (instance == null) {
            instance = new EmployeeDao();
        }
        return instance;
    }
}
