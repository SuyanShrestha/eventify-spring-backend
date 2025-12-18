package com.eventify.core.email;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eventify.core.email.dto.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void send(EmailDTO emailDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());
            helper.setText(emailDTO.getTextContent(), emailDTO.getHtmlContent());

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email to " + emailDTO.getTo(), e);
        }
    }

    @Async
    public void sendWithAttachment(EmailDTO emailDTO, String attachmentPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());
            helper.setText(emailDTO.getTextContent(), emailDTO.getHtmlContent());

            if (attachmentPath != null && !attachmentPath.isBlank()) {
                java.io.File file = new java.io.File(attachmentPath);
                if (file.exists()) {
                    helper.addAttachment(file.getName(), file);
                    log.info("Attached file {} to email for {}", file.getName(), emailDTO.getTo());
                } else {
                    log.warn("Attachment file not found: {}", attachmentPath);
                }
            }

            mailSender.send(message);
            log.info("Email sent to {}", emailDTO.getTo());

        } catch (MessagingException e) {
            log.error("Failed to send email to {}", emailDTO.getTo(), e);
            throw new RuntimeException("Failed to send email to " + emailDTO.getTo(), e);
        }
    }

}