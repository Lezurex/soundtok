package com.lezurex.soundtok.server.security;

import com.lezurex.soundtok.server.database.entities.User;
import com.lezurex.soundtok.server.database.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User " + s + " not found!");
        }
        return new CustomUserDetails(user);
    }
}
