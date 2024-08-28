package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.exception.UserExists;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
//
//import static org.springframework.security.crypto.bcrypt.BCrypt.checkpw;
//import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;
//
//@Service
//public class AppUserServiceImpl implements AppUserService{
//
//    private AppUserRepository appUserRepository;
//
//    private JWTService jwtService;
//    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
//        this.appUserRepository = appUserRepository;
//        this.jwtService = jwtService;
//    }
//
//
//    @Override
//    public AppUserDto createUser(AppUserDto appUserDto) {
//
//        AppUser appUser = mapToEntity(appUserDto);
//        Optional<AppUser> opEmail = appUserRepository.findByEmail(appUser.getEmail());
//        if(opEmail.isPresent()) {
//            throw new UserExists("Email already exists");
//        }
//        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
//        if(opUsername.isPresent()) {
//            throw new UserExists("Username already exists");
//        }
//
//        String hashpw = BCrypt.hashpw(appUser.getPassword(),BCrypt.gensalt(5));
//        appUser.setPassword(hashpw);
//
//        AppUser saved = appUserRepository.save(appUser);
//        AppUserDto userDto = mapToDto(saved);
//        return userDto;
//    }
//
//    @Override
//    public String verifyLogin(LoginDto loginDto) {
//        Optional<AppUser> opUsername = appUserRepository.findByUsername(loginDto.getUsername());
//        if (opUsername.isPresent()) {
//            AppUser appUser = opUsername.get();
//            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
//                return jwtService.generateToken(appUser);
//            }
//        }
//            return null;
//    }
//
//
//    AppUser mapToEntity(AppUserDto dto){
//        AppUser user=new AppUser();
//        user.setName(dto.getName());
//        user.setEmail(dto.getEmail());
//        user.setUsername(dto.getUsername());
//        user.setPassword(dto.getPassword());
//        return user;
//    }
//    AppUserDto mapToDto(AppUser appUser){
//        AppUserDto userDto=new AppUserDto();
//        userDto.setId(appUser.getId());
//        userDto.setName(appUser.getName());
//        userDto.setUsername(appUser.getUsername());
//        userDto.setEmail(appUser.getEmail());
//        userDto.setPassword(appUser.getPassword());
//        return userDto;
//    }
//
//    }
//
//

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final JWTService jwtService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AppUserDto createUser(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);

        // Check for existing email or username
        if (appUserRepository.findByEmail(appUser.getEmail()).isPresent()) {
            throw new UserExists("Email already exists");
        }
        if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
            throw new UserExists("Username already exists");
        }

        // Hash the password with a generated salt
        String hashedPassword = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt());
        appUser.setPassword(hashedPassword);



        AppUser savedUser = appUserRepository.save(appUser);
        return mapToDto(savedUser);
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        if (opUser.isPresent()) {
            AppUser appUser = opUser.get();

            // Check if the password matches
            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
                return jwtService.generateToken(appUser);
            }
        }
        return null; // Consider using an exception instead
    }

    private AppUser mapToEntity(AppUserDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());// Raw password, will be hashed before saving
        user.setRole(dto.getRole());
        return user;
    }

    private AppUserDto mapToDto(AppUser appUser) {
        AppUserDto userDto = new AppUserDto();
        userDto.setId(appUser.getId());
        userDto.setName(appUser.getName());
        userDto.setUsername(appUser.getUsername());
        userDto.setEmail(appUser.getEmail());
        userDto.setPassword(appUser.getPassword());
        userDto.setRole(appUser.getRole());
        // Exclude password from DTO
        return userDto;
    }
}
