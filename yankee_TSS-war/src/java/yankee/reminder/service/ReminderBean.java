package yankee.reminder.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;

/**
 *
 * @author pradipgiri
 */
@ManagedBean(name = "reminderEmail")
@ViewScoped
public class ReminderBean implements Serializable {
    
    private String email;
    private String subject;
    private String message;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void send() {
        try {
            Reminder reminder = new Reminder();
            reminder.sendMail(email, subject, message);
        } catch (MessagingException e) {
        }
    }
    
    
    
}
