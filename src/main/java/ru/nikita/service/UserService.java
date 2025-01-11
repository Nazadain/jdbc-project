package ru.nikita.service;

import lombok.NoArgsConstructor;
import ru.nikita.dao.UserDao;
import ru.nikita.dto.CreateUserDto;
import ru.nikita.dto.UserDto;
import ru.nikita.entity.User;
import ru.nikita.exception.DaoException;
import ru.nikita.exception.ValidationException;
import ru.nikita.mapper.CreateUserMapper;
import ru.nikita.mapper.UserMapper;
import ru.nikita.validator.CreateUserValidator;
import ru.nikita.validator.ValidationResult;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserService {
    private static UserService instance;

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CreateUserValidator validator = CreateUserValidator.getInstance();

    public Optional<UserDto> login(String username, String password) {
        return userDao.findByName(username, password).map(userMapper::mapFrom);
    }

    public Long create(CreateUserDto createUserDto) {
        ValidationResult result = validator.isValid(createUserDto);

        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

        User user = createUserMapper.mapFrom(createUserDto);
        return userDao.save(user);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
}
