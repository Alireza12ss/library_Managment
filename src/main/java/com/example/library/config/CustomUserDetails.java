package com.example.library.config;

import com.example.library.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ensure user role is not null and prefix it with "ROLE_"
        if (user.getRole() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        }
        throw new IllegalStateException("User role is not defined");
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
        return true; // Customize if account expiration logic exists
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize if account lock logic exists
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize if account enable/disable logic exists
    }

}
