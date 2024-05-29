package com.mycompany.app.hotel_management.utils;




import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    public static void sendEmail(String recipientEmail, String subject, String message) throws Exception {
        final String from = "inodev.0604@gmail.com";
        final String password = "dwvlqswugrcostcl";

        // Cấu hình thông tin máy chủ email
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

       // Session
        Session session = Session.getInstance(properties, auth);

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientEmail));
            mimeMessage.setSubject(subject);
//            mimeMessage.setReplyTo(InternetAddress.parse(from, false));
            mimeMessage.setText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // send email
        Transport.send(mimeMessage);
    }
}
