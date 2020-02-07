package com.group21.continous_integration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import java.util.HashMap;

public class EmailService {

    private static String host = "smtp-mail.outlook.com";
    private static int port = 587;
    private static String username = "CISendBuildReport@outlook.com";
    private static String password = "byggarebob123";
    private static HashMap<String, String> users = new HashMap<String, String>();

    public EmailService(){
      users.put("paullowenstrom", "paulher@kth.se");
      users.put("johansettlin", "settlin@kth.se");
      users.put("perfah", "perfah@kth.se");
      users.put("oliver", "oli@kth.se");
    }

    public static void SendBuildSuccesfull(String sendTo, String branch){
      sendMail(sendTo, branch, "Build Succesfull");
    }

    public static void SendBuildFailure(String sendTo, String branch){
      sendMail(sendTo, branch, "Build Failed");
    }

    public static void sendMail(String sendTo, String branch, String subject) {
      System.out.println("Sending mail");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("CISendBuildReport@outlook.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(users.get(sendTo)));
                message.setSubject(subject);

                String msg =  "Branch: " + branch + " failed to build. Please check.";

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
