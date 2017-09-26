package yankee.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.to.Person;

@SessionScoped
@Named
public class AccountBean implements Serializable {

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @Inject
    private LoginBean logInBean;

    private Person person;
    private Date dateOfBirth;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Person getPerson() {
        person = personBusinessLogic.getPersonByName(logInBean.getUser().getName());
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void update() {
        System.out.println("Date of Birth is  " + dateOfBirth);
        
        System.out.println("Preferred Language is  " + person.getPreferredLanguage());
        if (dateOfBirth != null) {
            person.setDateOfBirth(dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());            
        }
        personBusinessLogic.updateDetails(person);
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        UploadedFile file = event.getFile();
        person.setPhoto(file.getContents());
    }
}
