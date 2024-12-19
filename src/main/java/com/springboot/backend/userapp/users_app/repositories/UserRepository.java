package com.springboot.backend.userapp.users_app.repositories;

import com.springboot.backend.userapp.users_app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

}
