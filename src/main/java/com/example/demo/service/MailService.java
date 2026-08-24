package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.controller.AdminController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
    private String sender;
	Logger logger = LoggerFactory.getLogger(MailService.class);

    public void sendPlainText(String to, String subject, String body) {
    	 try {

             SimpleMailMessage mailMessage =
                     new SimpleMailMessage();

             mailMessage.setFrom(sender);
             mailMessage.setTo(to);
             mailMessage.setText(body);
             mailMessage.setSubject(subject);

             mailSender.send(mailMessage);


         } catch (Exception e) {
              logger.info("mail failed: "+e.getMessage());
         }
    }

    public void sendHtml(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true means this is HTML
        mailSender.send(message);
    }
}