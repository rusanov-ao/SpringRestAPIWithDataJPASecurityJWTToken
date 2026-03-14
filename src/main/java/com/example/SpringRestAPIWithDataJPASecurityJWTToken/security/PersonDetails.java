package com.example.SpringRestAPIWithDataJPASecurityJWTToken.security;

import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Person;
import com.example.SpringRestAPIWithDataJPASecurityJWTToken.models.Role;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = person.getRole();

        if (role == null) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); // default role
        }
        return Collections.singleton(new SimpleGrantedAuthority(person.getRole().toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}
