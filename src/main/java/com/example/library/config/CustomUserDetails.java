package com.example.library.config;

import com.example.library.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming role is a string like "ADMIN" or "USER"
        return List.of(() -> "ROLE_" + user.getRole());  // Mapping role to authority
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Can be customized if you have expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Can be customized if you have lock logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Can be customized if you have expiration logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // Can be customized if you want to disable accounts
    }

    public User getUser() {
        return user;
    }
}
