/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author securonix
 */
public class EmailUtility {

    private Properties properties;
    private String senderEmail;
    private ArrayList<String> recipientEmails;
    private String host;
    private String port;
    private String auth;
    private String starttls;
    private String ssl;
    private String username;
    private String password;

    public EmailUtility() {
        InputStream input = null;
        try {
            //use this constructor when you want to use the properties file.
            //Initialize all values needed for the email to be sent using the properties file.
            properties = new Properties();
            input = EmailUtility.class.getResourceAsStream("email.properties");
            properties.load(input);
            senderEmail = properties.getProperty("senderEmail");
            String recipientemail = properties.getProperty("recipientEmail");
            //recipientemail will have comma separated values.
            String[] recpEmails = recipientemail.split("\\,");
            recipientEmails = new ArrayList<>();
            recipientEmails.addAll(Arrays.asList(recpEmails));
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException ex) {
            Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public EmailUtility(String from, ArrayList<String> to) {
        InputStream input = null;
        try {
            //use this constructor when you want to use the properties file.
            //Initialize all values needed for the email to be sent using the properties file.
            properties = new Properties();
            input = EmailUtility.class.getResourceAsStream("email.properties");
            properties.load(input);
            senderEmail = from;
            recipientEmails = to;
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException ex) {
            Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public EmailUtility(String from, String to) {
        InputStream input = null;
        try {
            //use this constructor when you want to use the properties file.
            //Initialize all values needed for the email to be sent using the properties file.
            properties = new Properties();
            input = EmailUtility.class.getResourceAsStream("email.properties");
            properties.load(input);
            senderEmail = from;
            recipientEmails = new ArrayList<>();
            recipientEmails.add(to);
            host = properties.getProperty("host").trim();
            port = properties.getProperty("port").trim();
            username = properties.getProperty("username").trim();
            password = properties.getProperty("password").trim();
        } catch (IOException ex) {
            Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean sendMail(String body, String subject, boolean usesAuthentication, boolean authTLS, boolean authSSL) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);

        if (authTLS) {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
        }

        if (authSSL) {
            props.put("mail.smtp.socketFactory.port", port.trim());
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
        }

        Session session = null;
        if (usesAuthentication) {
            props.setProperty("mail.smtp.user", username);
            props.setProperty("mail.smtp.password", password);
            session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            session = Session.getDefaultInstance(props);
        }

        if (session != null) {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                for (String recipients : recipientEmails) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
                }
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                return true;
            } catch (MessagingException ex) {
                ex.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public ArrayList<String> getRecipientEmails() {
        return recipientEmails;
    }

    public void setRecipientEmails(ArrayList<String> recipientEmails) {
        this.recipientEmails = recipientEmails;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
