package com.example.SpringRestAPIWithDataJPASecurityJWTToken.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Data // Lombok: генерирует геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor // Конструктор без аргументов (нужен для JPA)
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Для @Builder (приватный!)
@Builder // Паттерн Builder для удобного создания объектов
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;

    @Column(name = "password", nullable = false)
    @ToString.Exclude // Важно! Чтобы пароль не попал в логи через toString
    @EqualsAndHashCode.Exclude // Чтобы не участвовал в сравнении объектов
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
