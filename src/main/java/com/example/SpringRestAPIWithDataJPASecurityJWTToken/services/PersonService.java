package com.example.SpringRestAPIWithDataJPASecurityJWTToken.services;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonRequestDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonResponseDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Role;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.repositories.PeopleRepository;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.PersonNotFoundException;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.UsernameAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {


    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<PersonResponseDTO> findAll() {
        return peopleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonResponseDTO findById(Long id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Пользователь с id=" + id + " не найден"));
        return mapToResponse(person);
    }

    @Transactional
    public PersonResponseDTO create(PersonRequestDTO personDTO) {
        // Проверка на уникальность username
        if (peopleRepository.findByUsername(personDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        Person person = Person.builder()
                .username(personDTO.getUsername())
                .yearOfBirth(personDTO.getYearOfBirth())
                .password(passwordEncoder.encode(personDTO.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        return mapToResponse(peopleRepository.save(person));
    }

    @Transactional
    public PersonResponseDTO update(Long id, PersonRequestDTO personDTO) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Пользователь с id=" + id + " не найден"));

        person.setUsername(personDTO.getUsername());
        person.setYearOfBirth(personDTO.getYearOfBirth());

        // Обновляем пароль только если он передан
        if (personDTO.getPassword() != null && !personDTO.getPassword().isEmpty()) {
            person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        }

        return mapToResponse(peopleRepository.save(person));
    }

    @Transactional
    public void delete(Long id) {
        if (!peopleRepository.existsById(id)) {
            throw new PersonNotFoundException("Пользователь с id=" + id + " не найден");
        }
        peopleRepository.deleteById(id);
    }

    private PersonResponseDTO mapToResponse(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getUsername(),
                person.getYearOfBirth(),
                person.getRole()
        );
    }
}
