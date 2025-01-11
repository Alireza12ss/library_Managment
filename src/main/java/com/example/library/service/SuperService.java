package com.example.library.service;

import com.example.library.config.CustomUserDetails;
import com.example.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
public class SuperService {
    UserRepository userRepository;

    protected Long getCurrentUserId() {
        String username = getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getId();
    }

    protected String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("Unable to retrieve username from SecurityContext");
    }
}
