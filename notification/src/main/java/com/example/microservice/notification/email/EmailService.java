package com.example.microservice.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.example.microservice.notification.email.EmailTemplates.CUSTOMER_CONFIRMATION;
import static com.example.microservice.notification.email.EmailTemplates.FRAUD_CONFIRMATION;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendCustomerSuccessEmail(
            String destinationEmail,
            String customerName,
            String registerDateTime) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("dilshangames@gmail.com");

        final String templateName = CUSTOMER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("customerEmail", destinationEmail);
        variables.put("registerDateTime", registerDateTime);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(CUSTOMER_CONFIRMATION.getTemplate());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully send to %s with template %s", destinationEmail, templateName));
        }catch (MessagingException e){
            log.warn("WARNING - Cannot send email to {}", destinationEmail);
        }
    }

    @Async
    public void sendFraudSuccessEmail(
            String destinationEmail,
            Boolean isFraudster,
            String registerDateTime) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("dilshangames@gmail.com");

        final String templateName = FRAUD_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("isFraudster", isFraudster);
        variables.put("customerEmail", destinationEmail);
        variables.put("registerDateTime", registerDateTime);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(FRAUD_CONFIRMATION.getTemplate());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully send to %s with template %s", destinationEmail, templateName));
        }catch (MessagingException e){
            log.warn("WARNING - Cannot send email to {}", destinationEmail);
        }
    }
}
