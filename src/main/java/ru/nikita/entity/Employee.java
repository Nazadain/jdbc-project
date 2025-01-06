package ru.nikita.entity;

import lombok.*;

@ToString
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Company company;
}
