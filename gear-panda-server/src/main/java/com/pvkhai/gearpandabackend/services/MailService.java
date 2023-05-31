package com.pvkhai.gearpandabackend.services;

import com.pvkhai.gearpandabackend.models.ResponseObject;
import org.springframework.http.ResponseEntity;


public interface MailService {
    ResponseEntity<ResponseObject> sendTextMail(Long id, String codeOrders, String body);

    ResponseEntity<ResponseObject> sendHTMLMail(Long id, String codeOrders, String subject);

}
