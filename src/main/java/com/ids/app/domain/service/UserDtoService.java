package com.ids.app.domain.service;

import com.ids.app.domain.UserDto;
import com.ids.app.persistence.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDtoService {

    private final Log LOGGER = LogFactory.getLog(UserDtoService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserDtoService(UserRepository userRepository){

        this.userRepository = userRepository;
    }
    public List<UserDto>getByColumns(){
        return userRepository.getByColumns();
    }
    public List<UserDto> getAll(){
        LOGGER.info("User Service :: getAll()");
        return userRepository.getAll();
    }
    public Optional<UserDto> getById(long id){
        return userRepository.getById(id);
    }
    public Optional<UserDto> getByEmail(String email){
        return userRepository.getByEmail(email);
    }
    public UserDto save(UserDto user){
        LOGGER.info("saving user information...");
        return userRepository.save(user);
    }
    public boolean delete(long id){
        return getById(id).map(user -> {
            userRepository.delete(id);
            return true;
        }).orElse(false);
    }
}
