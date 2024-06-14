package com.ids.app.web.controller;

import com.ids.app.domain.UserDto;
import com.ids.app.domain.service.UserDtoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UsersControllerPost {
    private final Log LOGGER = LogFactory.getLog(UsersControllerPost.class);
    private final UserDtoService userDtoService;

    @Autowired
    public UsersControllerPost(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id") long id) {
        if (userDtoService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {{
                put("message", "data successfully deleted");
            }});
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
            put("message", "something went wrong");
        }});
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto request) {
        if(!request.getUserEmail().isEmpty() && !request.getUserName().isEmpty()){
            if (userDtoService.update(request)){
                return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String,String>(){{
                    put("message","data successfully updated");
                }});
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String,String>(){{
                    put("message","something went wrong");
                }});
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String,String>(){{
                put("message","insufficient data provided");
            }});
        }
    }
}
