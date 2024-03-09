package com.ids.app.web.controller;

import com.ids.app.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send-email")
    public void sendEmail(@RequestParam String addressee,
                          @RequestParam String subject,
                          @RequestParam String content){
        emailService.sendEmail(addressee,subject,content);
    }
}
