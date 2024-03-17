package com.ids.app.web.controller;

import com.ids.app.domain.UserDto;
import com.ids.app.domain.dto.AuthenticationRequest;
import com.ids.app.domain.dto.RefreshTokenRequest;
import com.ids.app.domain.dto.UserRegistrationRequest;
import com.ids.app.domain.service.UserDtoService;
import com.ids.app.domain.service.UserService;
import com.ids.app.web.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthControllerPost {
    private final UserService userService;
    private final UserDtoService userDtoService;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthControllerPost(UserDtoService userDtoService, UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.userDtoService = userDtoService;
        this.jwtUtil = jwtUtil;

    }
    @PostMapping("/login")
    public ResponseEntity<?>LoginUser(@RequestBody AuthenticationRequest request){
        try {
            String token = userService.loginUser(request.getUser(), request.getPassword());
            UserDto user  = userDtoService.getByEmail(request.getUser()).get();
            String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashMap<String,String>(){{
                        put("refreshToken",refreshToken);
                        put("accessToken",token);
                    }});
        } catch (AuthenticationException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\":\""+ ex.getMessage()+"\"}");
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        try {
            UserDto user = userDtoService.getByEmail(jwtUtil.extractUsername(request.getToken())).get();
            String token = jwtUtil.generateToken(user.getUserId());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String,String>(){{
                put("refreshToken",refreshToken);
                put("accessToken",token);
            }});
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?>RegisterUser(@RequestBody UserRegistrationRequest request){
        try {
            if (request.getUserEmail().isEmpty() && request.getUserName().isEmpty() && request.getPassword().isEmpty()){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new HashMap<String,String>(){{
                            put("message","incomplete data entered");
                        }});
            }
            if(request.getPassword().length() < 8 ){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new HashMap<String,String>(){{
                            put("message","password is to short");
                        }});
            }
            if (!userDtoService.getByEmail(request.getUserEmail()).isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new HashMap<String,String>(){{
                            put("message", "this account already exists");
                        }});
            }
            UserDto user = new UserDto();
            user.setUserEmail(request.getUserEmail());
            user.setUserName(request.getUserName());
            user.setUserPassword(request.getPassword());
            userService.RegisterUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashMap<String,String>(){{
                        put("message","create success");
                    }});
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HashMap<String,String>(){{
                        put("message","this account already exists");
                    }});
        }
    }
}
