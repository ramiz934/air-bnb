package com.airbnb.controller;
import com.airbnb.entity.AppUser;
import com.airbnb.exception.UserExists;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.JWTToken;
import com.airbnb.payload.LoginDto;
import com.airbnb.service.AppUserService;
import com.airbnb.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AppUserService appUserService;

    public AuthController(AppUserService appUserService, com.airbnb.service.JWTService jwtService, JWTService jwtService1) {
        this.appUserService = appUserService;
    }

    @PostMapping("/createuser")
    public ResponseEntity<AppUserDto> createUser(
            @RequestBody AppUserDto appUserDto){

        appUserDto.setRole("ROLE_USER");
        AppUserDto dto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/createpropertyowner")
    public ResponseEntity<AppUserDto> createPropertyOwner(
            @RequestBody AppUserDto appUserDto){

        appUserDto.setRole("ROLE_OWNER");
        AppUserDto dto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PostMapping("/createpropertymanager")
    public ResponseEntity<AppUserDto> createPropertyManager(
            @RequestBody AppUserDto appUserDto){

        appUserDto.setRole("ROLE_MANAGER");
        AppUserDto dto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
@PostMapping("/login")
    public ResponseEntity<?> signIn(
            @RequestBody LoginDto loginDto) {
    String token = appUserService.verifyLogin(loginDto);
    JWTToken jwtToken=new JWTToken();
    if (token!=null) {
        jwtToken.setTokenType("JWT");
        jwtToken.setToken(token);
        return new ResponseEntity<>(jwtToken,HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Invalid username/password",HttpStatus.UNAUTHORIZED);
    }
}
}
