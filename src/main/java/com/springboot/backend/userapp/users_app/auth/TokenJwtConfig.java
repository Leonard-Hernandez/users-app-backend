package com.springboot.backend.userapp.users_app.auth;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    public static final String CONTENT_TYPE = "application/json";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

}
