package yankee.services;

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
}
