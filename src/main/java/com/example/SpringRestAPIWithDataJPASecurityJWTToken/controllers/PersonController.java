package com.example.SpringRestAPIWithDataJPASecurityJWTToken.controllers;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonRequestDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.dto.PersonResponseDTO;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // ✅ GET /people - список всех пользователей (доступно USER и ADMIN)
    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> getPeople() {
        List<PersonResponseDTO> people = personService.findAll();
        return ResponseEntity.ok(people);
    }

    // ✅ GET /people/{id} - один пользователь по ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> getPerson(@PathVariable Long id) {
        PersonResponseDTO person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    // ✅ POST /people - создание нового пользователя
    @PostMapping
    public ResponseEntity<PersonResponseDTO> createPerson(
            @RequestBody @Valid PersonRequestDTO personDTO) {
        PersonResponseDTO person = personService.create(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    // ✅ PUT /people/{id} - обновление пользователя
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> updatePerson(
            @PathVariable Long id,
            @RequestBody @Valid PersonRequestDTO personDTO) {
        PersonResponseDTO person = personService.update(id, personDTO);
        return ResponseEntity.ok(person);
    }

    // ✅ DELETE /people/{id} - удаление пользователя (только ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
