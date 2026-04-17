package com.example.SpringRestAPIWithDataJPASecurityJWTToken.services;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonResponseDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.repositories.PeopleRepository;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.util.PersonNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final PeopleRepository peopleRepository;

    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')") // Без префикса ROLE_
    @Transactional(readOnly = true)
    public List<PersonResponseDTO> getAllUsers() {
        return peopleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        if (!peopleRepository.existsById(id)) {
            throw new PersonNotFoundException("Пользователь с id=" + id + " не найден");
        }
        peopleRepository.deleteById(id);
    }

    // Маппинг внутри сервиса
    private PersonResponseDTO mapToResponse(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getUsername(),
                person.getYearOfBirth(),
                person.getRole()
        );
    }
}
