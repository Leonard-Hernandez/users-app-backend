package com.springboot.backend.userapp.users_app.repositories;

import com.springboot.backend.userapp.users_app.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
