package jee17.logic.to;

import java.time.LocalDate;

public class Person extends Named {

    private static final long serialVersionUID = 2813983198416172587L;

    private String firstName;    
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    
    
    public Person(String uuid, String name) {
        super(uuid, name);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
