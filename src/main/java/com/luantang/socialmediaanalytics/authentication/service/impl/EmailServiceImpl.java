package com.luantang.socialmediaanalytics.authentication.service.impl;

import com.luantang.socialmediaanalytics.authentication.exception.EmailConfirmationTokenNotFoundException;
import com.luantang.socialmediaanalytics.authentication.exception.FailedToSendEmailException;
import com.luantang.socialmediaanalytics.authentication.model.EmailConfirmationToken;
import com.luantang.socialmediaanalytics.authentication.repository.EmailConfirmationTokenRepository;
import com.luantang.socialmediaanalytics.authentication.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    private final String EMAIL_REGEX = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Autowired
    public EmailServiceImpl(EmailConfirmationTokenRepository confirmationTokenRepository, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public EmailConfirmationToken saveConfirmationToken(EmailConfirmationToken token) {
        return confirmationTokenRepository.save(token);
    }

    @Override
    public EmailConfirmationToken getConfirmationToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new EmailConfirmationTokenNotFoundException("Token not found"));
    }

    @Override
    @Async
    public void sendEmail(String to, String email) {
        Context context = new Context();
        context.setVariable("link", email);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setFrom("nair.app@gmail.com");
            helper.setTo(to);
            String htmlContent = templateEngine.process("email-template", context);
            helper.setText(htmlContent, true);
            helper.setSubject("Confirm your email");
            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            LOGGER.error("Failed to send email", ex);
            throw new FailedToSendEmailException("Failed to send email");
        }
    }
}
