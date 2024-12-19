package com.springboot.backend.userapp.users_app.services;

import com.springboot.backend.userapp.users_app.entities.User;
import com.springboot.backend.userapp.users_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        User user = optionalUser.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream().map( role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, authorities);
    }
}
