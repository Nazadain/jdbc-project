package ru.nikita.dto;

import lombok.Builder;
import lombok.Value;
import ru.nikita.entity.Role;

@Value
@Builder
public class UserDto {
    Long id;
    String username;
    String password;
    Role role;
}
