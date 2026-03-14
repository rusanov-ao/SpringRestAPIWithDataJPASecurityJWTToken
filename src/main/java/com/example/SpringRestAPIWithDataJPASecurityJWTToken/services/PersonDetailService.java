package com.example.SpringRestAPIWithDataJPASecurityJWTToken.services;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.repositories.PeopleRepository;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.security.PersonDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(PersonDetailService.class);

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user: {}", username);

        Person person = peopleRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", username);
                    return new UsernameNotFoundException("Пользователь '" + username + "' не найден");
                });

        return new PersonDetails(person);
    }
}
