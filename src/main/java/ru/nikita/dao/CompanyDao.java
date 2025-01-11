package ru.nikita.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.nikita.entity.Company;
import ru.nikita.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CompanyDao implements Dao<Long, Company> {
    private static CompanyDao instance;

    private static final String SAVE = "INSERT INTO company (name) VALUES (?)";

    private static final String FIND_ALL = "SELECT * FROM company";

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String UPDATE = "UPDATE company SET name = ? WHERE id = ?";

    private static final String DELETE = "DELETE FROM company WHERE id = ?";

    @Override
    @SneakyThrows
    public Long save(Company company) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(SAVE,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                company.setId(keys.getLong(1));
            }
            return company.getId();
        }
    }

    @Override
    @SneakyThrows
    public List<Company> findAll() {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL)
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(buildCompany(resultSet));
            }
            return companies;
        }
    }

    @SneakyThrows
    public Optional<Company> findById(Long id, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Company company = null;
            if (result.next()) {
                company = buildCompany(result);
            }
            return Optional.ofNullable(company);
        }
    }

    @Override
    @SneakyThrows
    public Optional<Company> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @Override
    @SneakyThrows
    public boolean update(Company entity) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
        ) {
            statement.setString(1, entity.getName());
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Long id) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(DELETE)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    public static CompanyDao getInstance() {
        if (instance == null) {
            instance = new CompanyDao();
        }
        return instance;
    }

    private static Company buildCompany(ResultSet result) throws SQLException {
        return new Company(
                result.getLong(1),
                result.getString(2));
    }
}
