package com.ids.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String addressee,String subject,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("no-replay@it.ids.com"));
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(addressee)));
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (MessagingException ex){
            ex.printStackTrace();
        }


    }
}
