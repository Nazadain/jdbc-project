package ru.nikita.validator;

import lombok.NoArgsConstructor;
import ru.nikita.dto.CreateUserDto;
import ru.nikita.entity.Role;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CreateUserValidator implements Validator<CreateUserDto> {
    private static CreateUserValidator instance;

    public ValidationResult isValid(CreateUserDto userDto) {
        ValidationResult result = new ValidationResult();
        if (Role.find(userDto.getRole()).isEmpty()) {
            result.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (userDto.getName().isEmpty() || userDto.getName().length() < 3) {
            result.add(Error.of("invalid.name",
                    "Name should be at least 3 characters"));
        }
        if (userDto.getPassword().isEmpty() || userDto.getPassword().length() < 3) {
            result.add(Error.of("invalid.password",
                    "Password should be at least 3 characters"));
        }
        return result;
    }

    public static CreateUserValidator getInstance() {
        if (instance == null) {
            instance = new CreateUserValidator();
        }
        return instance;
    }
}
