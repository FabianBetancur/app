package com.ids.app.domain.service;

import com.ids.app.domain.UserDto;
import com.ids.app.persistence.UserRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final Log LOGGER = LogFactory.getLog(UserDetailsService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user  = userRepository.getByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user: " + email + " not found"));
        LOGGER.info("User Detail Service :: result "+user.getUserEmail());
        String[] roles = {};

        return User.builder()
                .authorities(this.grantedAuthorities(roles))
                .username(user.getUserEmail())
                .password(user.getUserPassword())
                .build();
    }
    public List<GrantedAuthority> grantedAuthorities (String[] roles){
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
        for (String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            for (String authority : this.getAuthorities(role)){
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
    private String[] getAuthorities (String role){
        if("ADMIN".equals(role) || "DEFAULT".equals(role)){
            return new String[] {"update_content"};
        }
        return new String[]{};
    }
}
