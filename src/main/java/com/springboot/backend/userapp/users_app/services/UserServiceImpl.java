package com.springboot.backend.userapp.users_app.services;

import com.springboot.backend.userapp.users_app.entities.User;
import com.springboot.backend.userapp.users_app.models.UserRequest;
import com.springboot.backend.userapp.users_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List) this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllPage(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(@NonNull Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()){
            User dbUser = userOptional.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setName(user.getName());
            dbUser.setLastname(user.getLastname());
            dbUser.setUsername(user.getUsername());

            return Optional.of(this.userRepository.save(dbUser));
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }


}
