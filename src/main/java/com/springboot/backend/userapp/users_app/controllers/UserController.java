package com.springboot.backend.userapp.users_app.controllers;

import com.springboot.backend.userapp.users_app.entity.User;
import com.springboot.backend.userapp.users_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(originPatterns = {"*"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> list(){
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> detail(@PathVariable Long id){
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public  ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return validation(result);
        }
        user.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validation(result);
        }
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isPresent()){
            User dbUser = userOptional.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setName(user.getName());
            dbUser.setLastname(user.getLastname());
            dbUser.setPassword(user.getPassword());
            dbUser.setUsername(user.getUsername());

            return ResponseEntity.ok(this.userService.save(dbUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        Optional<User> userOptional = this.userService.findById(id);
        if (userOptional.isPresent()){
            this.userService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
