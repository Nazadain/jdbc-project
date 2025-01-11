package ru.nikita.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.nikita.entity.Role;
import ru.nikita.entity.User;
import ru.nikita.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserDao implements Dao<Long, User> {
    private static UserDao instance;

    private static final String SAVE =
            "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

    private static final String FIND_ALL = "SELECT * FROM users";

    private static final String FIND_BY_NAME_AND_PASSWORD = FIND_ALL + " WHERE username = ? AND password = ?";

    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";

    @Override
    @SneakyThrows
    public Long save(User user) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            return user.getId();
        }
    }

    @Override
    @SneakyThrows
    public List<User> findAll() {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL)
        ) {
            ResultSet result = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(buildUser(result));
            }
            return users;
        }
    }

    @SneakyThrows
    public Optional<User> findByName(String username, String password) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(
                        FIND_BY_NAME_AND_PASSWORD)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Long id) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private static User buildUser(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("id"))
                .username(result.getString("username"))
                .password(result.getString("password"))
                .role(result.getString("role").equals("USER") ? Role.USER
                        : Role.ADMIN)
                .build();
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }
}
