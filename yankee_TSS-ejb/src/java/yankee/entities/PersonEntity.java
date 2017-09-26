/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import yankee.logic.ENUM.PreferredLanguageENUM;

/**
 *
 * @author Sabs
 */
@NamedQueries({
    @NamedQuery(name = "getPersonCount", query = "SELECT COUNT(p) FROM PersonEntity p"),
    @NamedQuery(name = "getPersonList", query = "SELECT p FROM PersonEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getPersonByName", query = "SELECT p FROM PersonEntity p WHERE p.name = :name")
})

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
@Entity
@Table(name = "PERSON")
public class PersonEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private PreferredLanguageENUM preferredLanguage;
    private String userRoleRealm;
    
    @Lob
    private byte[] photo;

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    @OneToMany(mappedBy = "person")
    private Set<RoleEntity> roles;   

    public PersonEntity() {
    }
    
    public PersonEntity(boolean isNew){
        super(isNew);        
    }
    

    public String getUserRoleRealm() {
        return userRoleRealm;
    }

    public void setUserRoleRealm(String userRoleRealm) {
        this.userRoleRealm = userRoleRealm;
    }

    public PreferredLanguageENUM getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(PreferredLanguageENUM preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }    
   
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
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
