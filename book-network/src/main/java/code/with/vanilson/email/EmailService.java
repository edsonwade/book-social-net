package code.with.vanilson.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine engine;

    @Async // This annotation allows the method to be executed asynchronously
    public void send(String to,
                     String username,
                     EmailTemplateName emailTemplate,
                     String confirmationUrl,
                     String activationCode,
                     String subject) throws MessagingException {

        var templateName = emailTemplate == null ? "confirm-email" : emailTemplate.name();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);

        var context = new Context();
        context.setVariables(properties);

        helper.setFrom("vanilsonmuhongo@outlook.pt");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = engine.process(templateName, context);
        helper.setText(template, true);

        mailSender.send(message);

    }
}
