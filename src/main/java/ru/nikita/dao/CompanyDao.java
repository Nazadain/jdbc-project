package ru.nikita.dao;

import ru.nikita.entity.Company;
import ru.nikita.exception.DaoException;
import ru.nikita.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CompanyDao implements Dao<Long, Company> {
    private static CompanyDao instance;

    private static final String SAVE = "INSERT INTO company (name) VALUES (?)";

    private static final String FIND_ALL = "SELECT * FROM company";

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    private static final String UPDATE = "UPDATE company SET name = ? WHERE id = ?";

    private static final String DELETE = "DELETE FROM company WHERE id = ?";

    @Override
    public Long save(Company company) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                company.setId(keys.getLong(1));
            }
            return company.getId();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(buildCompany(resultSet));
            }
            return companies;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Company> findById(Long id, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Company company = null;
            if (result.next()) {
                company = buildCompany(result);
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Company entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private CompanyDao() {
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
