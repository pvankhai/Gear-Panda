package com.pvkhai.gearpandabackend.services.impl;

import com.pvkhai.gearpandabackend.constants.MailConstants;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.repositories.UserRepository;
import com.pvkhai.gearpandabackend.services.MailService;
import com.pvkhai.gearpandabackend.services.ThymeleafService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    ThymeleafService thymeleafService;
    @Autowired
    UserRepository userRepository;


    /**
     * Send notifications to the user's Gmail address
     *
     * @param id
     * @param codeOrders
     * @param body
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> sendTextMail(Long id, String codeOrders, String body) {
        if (!userRepository.findById(id).isEmpty()) {
            if (userRepository.findById(id).get().getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("FAILED", "Not found email!", "N/A")
                );
            } else {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(MailConstants.SENDER_EMAIL);
                mailMessage.setTo(userRepository.findById(id).get().getEmail());
                mailMessage.setText("Hello " + userRepository.findById(id).get().getFirstName() + ",\n" +
                        "Your order failed to deliver due to cancellation or lost in transit");

                mailMessage.setSubject("Order " + codeOrders + " has a shipping problem! ");
                mailSender.send(mailMessage);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Sent invoice information to " + "'" + mailMessage.getTo() + "'" + " successfully", mailMessage)
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("FAILED", "Not found user!", "N/A")
        );
    }


    /**
     * Send order information to the user's Gmail address
     *
     * @param codeOrders
     * @param subject
     * @return
     */
    @Override
    public ResponseEntity<ResponseObject> sendHTMLMail(Long id, String codeOrders, String subject) {
        if (!userRepository.findById(id).isEmpty()) {
            if (userRepository.findById(id).get().getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("FAILED", "Not found email!", "N/A")
                );
            } else {
                MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                        messageHelper.setFrom(MailConstants.SENDER_EMAIL);
                        messageHelper.setTo(userRepository.findById(id).get().getEmail());
                        messageHelper.setSubject("GearPanda received the order " + codeOrders);
                        messageHelper.setText(thymeleafService.getContent(thymeleafService.getContent(userRepository.findById(id).get().getFirstName())), true);
                    }
                };
                mailSender.send(mimeMessagePreparator);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Sent invoice information to " + "'" + userRepository.findById(id).get().getEmail() + "'" + " successfully", mimeMessagePreparator)
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("FAILED", "Not found user!", "N/A")
        );
    }

}
