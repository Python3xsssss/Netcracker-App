package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public User loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userService.getUserByUsername(username);
    }
}