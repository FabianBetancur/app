package com.ids.app.web.controller;

import com.ids.app.domain.service.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UsersControllerPost {
    private final UserDtoService userDtoService;
    @Autowired
    public UsersControllerPost(UserDtoService userDtoService){
        this.userDtoService = userDtoService;
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?>delete(@RequestParam(name = "id")long id){
        if(userDtoService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String,String>(){{
                put("message","data successfully deleted");
            }});
        }
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String,String>(){{
            put("message","something went wrong");
        }});
    }
}
