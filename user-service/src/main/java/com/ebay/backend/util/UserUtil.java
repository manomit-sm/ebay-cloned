package com.ebay.backend.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SecureRandom;

@Component
public class UserUtil {

    private final String sendGridApiKey;

    public UserUtil(
            @Value("${sendgrid.apiKey}") final String sendGridApiKey
    ) {
        this.sendGridApiKey = sendGridApiKey;
    }

    public int getEmailVerificationCode() {
        SecureRandom secureRandom = new SecureRandom();
        return 100000 + secureRandom.nextInt(900000);
    }

    @Async
    public void sendVerificationEmail(String toEmail, int verificationCode) throws IOException {
        Email from = new Email("manomit@bsolz.net");
        Email to = new Email(toEmail);
        String subject = "Verify your account";
        Content content = new Content("text/html", "<p>Your code is <b>" + verificationCode + "</b></p>");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
