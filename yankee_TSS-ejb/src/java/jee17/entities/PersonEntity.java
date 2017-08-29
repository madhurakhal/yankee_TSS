/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Sabs
 */
@NamedQueries({
    @NamedQuery(name = "getPersonCount", query = "SELECT COUNT(p) FROM PersonEntity p"),
    @NamedQuery(name = "getPersonList", query = "SELECT p FROM PersonEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getPersonByName", query = "SELECT p FROM PersonEntity p WHERE p.name = :name")
})
@Entity
@Table(name = "PERSON")
public class PersonEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    
    @OneToMany(mappedBy = "roles")
    private Set<String> role;

    public PersonEntity() {
    }
    
    public PersonEntity(boolean isNew){
        super(isNew);        
    }

    
    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
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
