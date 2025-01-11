package ru.nikita.mapper;

import lombok.NoArgsConstructor;
import ru.nikita.dto.UserDto;
import ru.nikita.entity.Role;
import ru.nikita.entity.User;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserDto, User> {
    private static UserMapper instance;

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static UserMapper getInstance() {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }
}
