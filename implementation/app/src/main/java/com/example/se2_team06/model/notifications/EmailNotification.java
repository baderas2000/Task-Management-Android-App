package com.example.se2_team06.model.notifications;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailNotification extends Notification {

    private String emailReceiverAddress;

    public EmailNotification(String emailReceiver) {
        this.emailReceiverAddress = emailReceiver;
    }

    @Override
    public void sendNotification() {

        Properties emailProperties = getProperties();
        Session session = getSession(emailProperties);
        AsyncTask.execute(() -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("task-manager-application@example.com"));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(emailReceiverAddress));
                message.setSubject(getNotificationHeaderText());

                String msg = getNotificationText();

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                Transport.send(message);
                Log.d("EmailNotification", "Email finally sent");
            } catch (Exception e) {
                Log.e("EmailNotification", "failed to send an email", e);
            }
        });
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525");
        properties.put("mail.smtp.ssl", "no");
        properties.put("mail.smtp.tls", "yes");
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        return properties;
    }

    private Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dd9bb40b65092a", "a10979eaae9fac");
            }
        });
    }
}
