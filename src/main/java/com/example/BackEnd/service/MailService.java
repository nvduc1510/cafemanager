package com.example.BackEnd.service;

import com.example.BackEnd.dto.DataMailDTO;
import com.example.BackEnd.dto.request.RegisterRequest;

import javax.mail.MessagingException;

public interface MailService {
//    Boolean createUser (RegisterRequest request);
    void sendHtmlMail (DataMailDTO mailDTO, String templateMail) throws MessagingException;
}
