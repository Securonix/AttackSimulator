/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feedgeneratorui;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author securonix
 */
public class SendMail {
    private String email;
    private String subject;
    private String message;
    private Properties props = null;
    private Session session = null;
    private String username = null;
    
    public SendMail(String em, String sub, String mess, String username) {
        email = em;
        subject = sub;
        message = mess;
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        try{
            session = Session.getInstance(props,
                      new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("info@securonix.com", "open24X7");
                            }
                      });
        }catch(Exception e){
            e.printStackTrace();
        }
        this.username = username;
    }
    
    public boolean sendMail(){
        try{
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("info@securonix.com", "Securonix.com Administrator"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, username));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            return true;
        }catch(AddressException ex){
            ex.printStackTrace();
        }catch(MessagingException ex){
            ex.printStackTrace();
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        
        return false;
    }
    
    
}
