package com.springboot.backend.userapp.users_app.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRequest implements IUser {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 12)
    private String username;
    private boolean admin;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email String email) {
        this.email = email;
    }

    public @NotBlank String getLastname() {
        return lastname;
    }

    public void setLastname(@NotBlank String lastname) {
        this.lastname = lastname;
    }

    public @NotBlank @Size(min = 6, max = 12) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 6, max = 12) String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
