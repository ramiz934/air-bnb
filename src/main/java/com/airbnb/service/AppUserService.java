package com.airbnb.service;

import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;

public interface AppUserService {


    AppUserDto createUser(AppUserDto appUserDto);

    String verifyLogin(LoginDto loginDto);
}
