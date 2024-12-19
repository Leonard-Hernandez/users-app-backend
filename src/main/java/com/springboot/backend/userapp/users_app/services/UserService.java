package com.springboot.backend.userapp.users_app.services;

import com.springboot.backend.userapp.users_app.entities.User;
import com.springboot.backend.userapp.users_app.models.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(@NonNull Long id);

    Page<User> findAllPage(Pageable pageable);

    User save(User user);

    Optional<User> update(UserRequest user, Long id);

    void deleteById(Long id);

}
