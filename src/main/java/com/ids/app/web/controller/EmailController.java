package com.ids.app.web.controller;

import com.ids.app.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send-email")
    public ResponseEntity<?>sendEmail(@RequestParam String content){
        try {
            String addressee = "edfabianb@gmail.com";
            String subject = "contact";
            emailService.sendEmail(addressee,subject,content);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashMap<String,String>(){{
                        put("message","message sent successfully");
                    }});
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HashMap<String,String>(){{
                        put("message",ex.getMessage());
                    }});
        }

    }
}
