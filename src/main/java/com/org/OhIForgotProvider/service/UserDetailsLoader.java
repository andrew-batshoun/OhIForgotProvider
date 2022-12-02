package com.org.OhIForgotProvider.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.model.UserWithRoles;
import com.org.OhIForgotProvider.respository.UserRepository;

@Service
public class UserDetailsLoader implements UserDetailsService {

	private final UserRepository users;

    public UserDetailsLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user);
    }
}
