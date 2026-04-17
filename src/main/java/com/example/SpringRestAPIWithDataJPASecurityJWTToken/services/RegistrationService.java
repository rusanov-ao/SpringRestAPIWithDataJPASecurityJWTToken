package com.example.SpringRestAPIWithDataJPASecurityJWTToken.services;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonRequestDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Role;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.repositories.PeopleRepository;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Person register(PersonRequestDTO personDTO) {
        // Проверка на существующего пользователя
        if (peopleRepository.findByUsername(personDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        Person person = Person.builder()
                .username(personDTO.getUsername())
                .yearOfBirth(personDTO.getYearOfBirth())
                .password(passwordEncoder.encode(personDTO.getPassword())) // ✅ Хеширование
                .role(Role.ROLE_USER)
                .build();

        return peopleRepository.save(person); // Возвращаем сохранённого пользователя
    }
}
