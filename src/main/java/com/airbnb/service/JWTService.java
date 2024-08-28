package com.airbnb.service;


import com.airbnb.entity.AppUser;
import com.airbnb.payload.JWTToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    private static  final String USER_NAME="username";

    @PostConstruct
    public void postContruct() throws UnsupportedEncodingException {
        algorithm=Algorithm.HMAC256(algorithmKey);
    }
    //computer Engineer is good (for remembering)//token is String,so we are returning String
    public String generateToken(AppUser user) {
        return JWT.create().
                withClaim(USER_NAME, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    //to verify the token which is extracted
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);  //joky rocky with builder vikram(to remember)
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
