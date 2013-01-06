package com.iriand.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class GMail implements Email {
    private final String host = "smtp.gmail.com";
    private String username = "";
    private String password = "";

    public GMail(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void send(String from, String[] recipientsTo, String[] recipientsCC, String[] recipientsBCC, String subject, String body, String[] files) {

        try {
            Properties properties = System.getProperties();
            setMailServerProperties(username, password, host, properties);

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            setAllRecipients(recipientsTo, recipientsCC, recipientsBCC, message);
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            setAttachments(files, multipart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void setAttachments(String[] files, Multipart multipart) throws MessagingException {
        if (files != null) {
            for (String file : files) {
                BodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file);
                multipart.addBodyPart(messageBodyPart);
            }
        }
    }

    private static void setAllRecipients(String[] recipientsTo, String[] recipientsCC, String[] recipientsBCC, MimeMessage message) throws MessagingException {
        setRecipients(recipientsTo, MimeMessage.RecipientType.TO, message);
        setRecipients(recipientsCC, MimeMessage.RecipientType.CC, message);
        setRecipients(recipientsBCC, MimeMessage.RecipientType.BCC, message);
    }

    private static void setRecipients(String[] recipients, Message.RecipientType recipientsType, MimeMessage message) throws MessagingException {
        if (recipients != null) {
            for (String recipient : recipients) {
                message.addRecipient(recipientsType, new InternetAddress(recipient));
            }
        }
    }

    private static void setMailServerProperties(String username, String password, String host, Properties properties) {
        properties.setProperty("mail.user", username);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.password", password);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }
}
