package com.springboot.backend.userapp.users_app.repositories;

import com.springboot.backend.userapp.users_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

}
