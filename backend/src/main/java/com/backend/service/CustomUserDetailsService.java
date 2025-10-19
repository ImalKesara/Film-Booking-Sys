package com.backend.service;

import com.backend.model.User;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService(){

        return username -> userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found..."));
    }
}
