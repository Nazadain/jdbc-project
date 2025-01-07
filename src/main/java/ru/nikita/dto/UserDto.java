package ru.nikita.dto;

import lombok.*;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
}
