package ru.nikita.mapper;

import ru.nikita.dto.CreateUserDto;
import ru.nikita.entity.Role;
import ru.nikita.entity.User;

public final class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static CreateUserMapper instance;

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .username(createUserDto.getName())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .build();
    }

    public static synchronized CreateUserMapper getInstance() {
        if (instance == null) {
            instance = new CreateUserMapper();
        }
        return instance;
    }
}
