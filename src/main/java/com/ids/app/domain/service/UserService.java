package com.ids.app.domain.service;

import com.ids.app.domain.UserDto;
import com.ids.app.web.security.JwtUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final Log LOGGER = LogFactory.getLog(UserService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDtoService userDtoService;
    @Autowired
    public UserService (PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtUtil jwtUtil,UserDtoService userDtoService){
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDtoService = userDtoService;
    }
    public String loginUser(String userName,String password) throws AuthenticationException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        UserDto user  = userDtoService.getByEmail(userName).get();
        return jwtUtil.generateToken(user.getUserId());
    }

    public UserDto RegisterUser(UserDto user){
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        return userDtoService.save(user);
    }
}
