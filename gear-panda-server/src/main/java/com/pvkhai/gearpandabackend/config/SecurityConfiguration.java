package com.pvkhai.gearpandabackend.config;


import com.pvkhai.gearpandabackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
//        httpSecurity.authorizeRequests()
////                        .requestMatchers("/api/v1/user/**").hasAuthority("ROLE_USER")
//                .requestMatchers("/**").permitAll()
//                .anyRequest().authenticated();
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied")
//                .and()
//                .authenticationProvider(authenticationProvider());

        return httpSecurity.build();
    }

}
