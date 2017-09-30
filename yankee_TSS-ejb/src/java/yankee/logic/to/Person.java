package yankee.logic.to;

import java.time.LocalDate;
import java.util.ArrayList;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.PreferredLanguageENUM;

public class Person extends Named {

    private static final long serialVersionUID = 2813983198416172587L;

    private byte[] photo;

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    private String firstName;    
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    
    private String contractUUIDForRole;
    private ContractStatusEnum contractStatusForRole;

    public ContractStatusEnum getContractStatusForRole() {
        return contractStatusForRole;
    }

    public void setContractStatusForRole(ContractStatusEnum contractStatusForRole) {
        this.contractStatusForRole = contractStatusForRole;
    }

    public String getContractUUIDForRole() {
        return contractUUIDForRole;
    }

    public void setContractUUIDForRole(String contractUUIDForRole) {
        this.contractUUIDForRole = contractUUIDForRole;
    }
    
    private ArrayList<Role> roles;
    
    private PreferredLanguageENUM preferredLanguage;
    private String userRoleRealm;

    public PreferredLanguageENUM getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(PreferredLanguageENUM preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getUserRoleRealm() {
        return userRoleRealm;
    }

    public void setUserRoleRealm(String userRoleRealm) {
        this.userRoleRealm = userRoleRealm;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
    
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
    
    @Override
    public String toString() {
        return String.format(firstName + ' ' + lastName );
    }
}
