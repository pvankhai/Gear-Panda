package com.pvkhai.gearpandabackend.controllers;

import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/mail")
public class MailController {
    @Autowired
    MailService mailService;

    // Send notifications as text
    @PostMapping("/notifyEmail")
    public ResponseEntity<ResponseObject> sendSimpleEmail(@RequestParam Long id, @RequestParam String codeOrders, @RequestParam String body) {
        return mailService.sendTextMail(id, codeOrders, body);
    }

    // Send order information as 1 Thymeleaf
    @PostMapping("/orderEmail")
    public ResponseEntity<ResponseObject> sendHtmlEmail(@RequestParam Long id, @RequestParam String codeOrders, @RequestParam String subject) {
        return mailService.sendHTMLMail(id, codeOrders, subject);
    }

}