package com.example.BackEnd.service.impl;

import com.example.BackEnd.dto.DataMailDTO;
import com.example.BackEnd.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Override
    public void sendHtmlMail(DataMailDTO mailDTO, String templateMail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {
            // Set the sender email address
//            helper.setFrom("your-email@example.com");
            // Process Thymeleaf template
            Context context = new Context();
            context.setVariables(mailDTO.getProps());
            // Retrieve the processed HTML content from the Thymeleaf template
            String htmlContent = templateEngine.process(templateMail, context);
            // Set the recipient email address
            helper.setTo(mailDTO.getTo());
            // Set the email subject
            helper.setSubject(mailDTO.getSubject());
            // Set the HTML content of the email
            helper.setText(htmlContent, true);
            // Send the email
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exceptions, log, or rethrow as needed
            throw new MessagingException("Error sending HTML email", e);
        }
    }
}