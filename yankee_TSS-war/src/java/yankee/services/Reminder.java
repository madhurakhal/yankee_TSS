package yankee.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author pradipgiri
 */
@Stateless
public class Reminder implements Serializable{
    
    @Resource(lookup = "mail/uniko-mail")
    private Session mailSession;
    
    
    public void sendMail(String recipient, String subject, String message) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipient, false));
        msg.setFrom(InternetAddress.parse("team-system@no.where")[0]);
        msg.setText(message);
        Transport.send(msg);
    }
    
    public void sendMailFromGmail(String recipient, String subject, String message) throws MessagingException {
//        Properties props=System.getProperties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//
//        Session mailSession=Session.getDefaultInstance(props, null);
//        mailSession.setDebug(true);
//
//        Message mailMessage=new MimeMessage(mailSession);
//
//        mailMessage.setFrom(new InternetAddress("itsmepradipgiri@gmail.com"));
//        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//        mailMessage.setContent(message,"text/html");
//        mailMessage.setSubject(subject);
//
//        Transport transport=mailSession.getTransport("smtp");
//        transport.connect("smtp.gmail.com", "itsmepradipgiri@gmail.com", "password");
//        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());   
    }
    
}
