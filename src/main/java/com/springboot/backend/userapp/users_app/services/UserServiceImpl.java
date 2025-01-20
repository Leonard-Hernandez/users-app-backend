package com.springboot.backend.userapp.users_app.services;

import com.springboot.backend.userapp.users_app.entities.Role;
import com.springboot.backend.userapp.users_app.entities.User;
import com.springboot.backend.userapp.users_app.models.IUser;
import com.springboot.backend.userapp.users_app.models.UserRequest;
import com.springboot.backend.userapp.users_app.repositories.RoleRepository;
import com.springboot.backend.userapp.users_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) ((List<User>) this.userRepository.findAll()).stream().map(
                user -> {
                    boolean admin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
                    user.setAdmin(admin);
                    return user;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllPage(Pageable pageable) {
        return this.userRepository.findAll(pageable).map(  user -> {
            boolean admin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
            user.setAdmin(admin);
            return user;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(@NonNull Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {

        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);

    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User dbUser = userOptional.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setName(user.getName());
            dbUser.setLastname(user.getLastname());
            dbUser.setUsername(user.getUsername());

            dbUser.setRoles(getRoles(user));

            return Optional.of(this.userRepository.save(dbUser));
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        this.userRepository.deleteById(id);
        return id;
    }

    private List<Role> getRoles(IUser user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);

        }
        return roles;
    }

}
