package com.example.springsecurityapplication.security;

import com.example.springsecurityapplication.models.Person;
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
    public Person getPerson(){
        return this.person;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getLogin();
    }
//действителен? Y-N
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
//не заблочен? Y-N
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
//пароль действительный? Y-N
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
//Активен или нет акк? Y-N
    @Override
    public boolean isEnabled() {
        return true;
    }
}
