package com.example.SpringRestAPIWithDataJPASecurityJWTToken.util;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.services.PersonDetailService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailService personDetailService;

    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        try {
            personDetailService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException e) {
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
