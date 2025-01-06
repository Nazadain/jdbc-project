package ru.nikita.entity;

import lombok.*;

@ToString
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;
    private String name;
}
