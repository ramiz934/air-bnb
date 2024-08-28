package com.airbnb.config;

import com.airbnb.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain SecurityFilterChain(
            HttpSecurity http
    )throws Exception {

        //h(cd)2
        http.csrf().disable().cors().disable();

        //haap
        //http.authorizeHttpRequest().anyRequest().permitAll();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/createuser", "/api/v1/auth/login","/api/v1/auth/createpropertyowner")
                .permitAll()
                .requestMatchers("/api/v1/auth/createpropertymanager").hasRole("ADMIN")
                .requestMatchers("/api/v1/addproperty","/api/v1/property").hasAnyRole("OWNER","ADMIN","MANAGER")
                .anyRequest().authenticated();

        return http.build();

    }
}
//package com.airbnb.config;
//
//import org.apache.tomcat.util.http.parser.Authorization;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.intercept.AuthorizationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    private JWTFilter jwtFilter;
//
//    public SecurityConfig(JWTFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        //h(cd)*2
//        httpSecurity.csrf().disable().cors().disable();
//        // httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
//        httpSecurity.addFilterBefore(jwtFilter, AuthorizationFilter.class);
//        httpSecurity.authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/createuser",
//                        "/api/v1/auth/propertyowner","/api/v1/auth/login","/api/v1/country",
//                        "/api/v1/city").permitAll().
//                requestMatchers("/api/v1/admin","/api/v1/propertymanager").hasRole("ADMIN").
//                requestMatchers("/api/v1/addproperty","/api/v1/property/createproperty").hasAnyRole("OWNER","ADMIN","MANAGER")
//                .anyRequest().authenticated();
//        //Permit all the urls which is matched with the given url and authenticate any requests other than this url
//
//        return httpSecurity.build();
//    }
//}
